package com.krm.web.util.draw.io;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import net.coobird.thumbnailator.Thumbnails;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.mxgraph.canvas.mxGraphicsCanvas2D;
import com.mxgraph.canvas.mxICanvas2D;
import com.mxgraph.reader.mxSaxOutputHandler;
import com.mxgraph.util.mxUtils;
import com.objectplanet.image.PngEncoder;


/**
 *user the class for the draw io
 *
 *  Use xml what create image 
 *
 * @author taosq
 *
 */
public final class CreateImage {
	
	/**
	 * Cache for all images.
	 */
	private transient  Hashtable<String, Image> imageCache = new Hashtable<String, Image>();
	
	private transient  SAXParserFactory parserFactory = SAXParserFactory
	.newInstance();
	
	public static CreateImage getInstance(){
		return new CreateImage();
	}
	
	private CreateImage(){} 
	
	
	/**
	 * 
	 * @param width  the image's width
	 * @param height the image's height
	 * @param bg     the image's background
	 * @param format the image's format (example: png, gif,jpg,bpm etc.)
	 * @param os     the output stream (if the os is class of ServletOutputStream, return the file output stream to browser)
	 * @param xml    import input data
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public  void createImage(int width, int height,Color bg,String format, OutputStream os,String xml) throws SAXException, ParserConfigurationException, IOException{
		if(org.apache.commons.lang3.StringUtils.isBlank(xml))
			return;
		/**
		 * firefox浏览器随时更新，更新后这个标签他不再产生
		 */
		if(!xml.startsWith("<output>")){
			xml = "<output>" + xml + "</output>";
		}
		BufferedImage image = mxUtils.createBufferedImage(width, height, bg);

		if (image != null) {
			Graphics2D g2 = image.createGraphics();
			mxUtils.setAntiAlias(g2, true, true);
			renderXml(xml, createCanvas(g2));

			// Uses faster PNG encoder
			if (format.equalsIgnoreCase("png")) {
				PngEncoder encoder = (bg != null) ? new PngEncoder()
						: new PngEncoder(PngEncoder.COLOR_TRUECOLOR_ALPHA);
				encoder.encode(image, os);
			} else {
				ImageIO.write(image, format,os);
			}
		}
	}
	
	/**
	 * create small picture
	 * @param resource
	 * @param dest
	 * @throws IOException
	 */
	public void createThumbnails(File resource, File dest) throws IOException{
		Thumbnails.of(resource).size(100, 100).toFile(dest);
	}
	
	/**
	 * create small picture
	 * @param resource
	 * @param dest
	 * @throws IOException
	 */
	public void createThumbnails(File resource, File dest,int w,int h) throws IOException{
		Thumbnails.of(resource).size(w, h).toFile(dest);
	}
	
	/**
	 * Renders the XML to the given canvas.
	 */
	protected  void renderXml(String xml, mxICanvas2D canvas)
			throws SAXException, ParserConfigurationException, IOException {
		XMLReader reader = parserFactory.newSAXParser().getXMLReader();
		reader.setContentHandler(new mxSaxOutputHandler(canvas));
		reader.parse(new InputSource(new StringReader(xml)));
	}
	
	/**
	 * Creates a graphics canvas with an image cache.
	 */
	private  mxGraphicsCanvas2D createCanvas(Graphics2D g2) {
		// Caches custom images for the time of the request
		final Hashtable<String, Image> shortCache = new Hashtable<String, Image>();

		mxGraphicsCanvas2D g2c = new mxGraphicsCanvas2D(g2) {
			public Image loadImage(String src) {
				// We can't do SSL connections currently
				if (src.startsWith("https://") && src.length() > 8) {
					src = "http://" + src.substring(8, src.length());
				}

				// Relative path handling
				if (!src.startsWith("http://")) {
					src = Constants.IMAGE_DOMAIN + src;
				}

				// Match old domains used for image hosting
				for (String domain : Constants.IMAGE_DOMAIN_MATCHES) {
					if (src.startsWith(domain)
							&& src.length() > domain.length()) {
						src = Constants.IMAGE_DOMAIN
								+ src.substring(domain.length(), src.length());
					}
				}

				// Uses local image cache by default
				Hashtable<String, Image> cache = shortCache;

				// Uses global image cache for all server-side images
				if (src.startsWith(Constants.IMAGE_DOMAIN)) {
					cache = imageCache;
				}

				Image image = cache.get(src);

				if (image == null) {
					image = super.loadImage(src);

					if (image != null) {
						cache.put(src, image);
					} else {
						cache.put(src, Constants.EMPTY_IMAGE);
					}
				} else if (image == Constants.EMPTY_IMAGE) {
					image = null;
				}

				return image;
			}
		};

		return g2c;
	}
	
}
