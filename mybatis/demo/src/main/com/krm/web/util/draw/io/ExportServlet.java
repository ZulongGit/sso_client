package com.krm.web.util.draw.io;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.FontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.mxgraph.canvas.mxGraphicsCanvas2D;
import com.mxgraph.canvas.mxICanvas2D;
import com.mxgraph.reader.mxSaxOutputHandler;
import com.mxgraph.util.mxBase64;
import com.mxgraph.util.mxUtils;

/**
 * Servlet implementation class ImageServlet.
 */
public class ExportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(ExportServlet.class
			.getName());

	static {
		Constants.IMAGE_DOMAIN_MATCHES.add("http://img.diagramly.com/");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5040708166131034515L;

	/**
	 * 
	 */
	private transient SAXParserFactory parserFactory = SAXParserFactory
			.newInstance();

	/**
	 * Cache for all images.
	 */
	private transient Hashtable<String, Image> imageCache = new Hashtable<String, Image>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExportServlet() {
		super();
	}

	/**
	 * Handles exceptions and the output stream buffer.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			if (request.getContentLength() < Constants.MAX_REQUEST_SIZE) {
				long t0 = System.currentTimeMillis();

				handleRequest(request, response);

				long mem = Runtime.getRuntime().totalMemory()
						- Runtime.getRuntime().freeMemory();
				long dt = System.currentTimeMillis() - t0;

				logger.info("export: ip=" + request.getRemoteAddr() + " ref=\""
						+ request.getHeader("Referer") + "\" length="
						+ request.getContentLength() + " mem=" + mem + " dt="
						+ dt);
			} else {
				response
						.setStatus(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
			}
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			final Runtime r = Runtime.getRuntime();
			logger.info("r.freeMemory() = " + r.freeMemory() / 1024.0 / 1024);
			logger.info("r.totalMemory() = " + r.totalMemory() / 1024.0 / 1024);
			logger.info("r.maxMemory() = " + r.maxMemory() / 1024.0 / 1024);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} finally {
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}

	/**
	 * Gets the parameters and logs the request.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws DocumentException
	 */
	protected void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Parses parameters
		request.setCharacterEncoding("utf-8");
	//	response.setCharacterEncoding("utf-8");
		String format = request.getParameter("format");
		String fname = request.getParameter("filename");
		int w = Integer.parseInt(request.getParameter("w"));
		int h = Integer.parseInt(request.getParameter("h"));
		String tmp = request.getParameter("bg");
		String xml = getRequestXml(request);

		Color bg = (tmp != null) ? mxUtils.parseColor(tmp) : null;

		// Checks parameters
		if (w > 0 && w <= Constants.MAX_WIDTH && h > 0
				&& h <= Constants.MAX_HEIGHT && format != null && xml != null
				&& xml.length() > 0) {
			// Allows transparent backgrounds only for PNG
			if (bg == null && !format.equals("png")) {
				bg = Color.WHITE;
			}

			if (fname != null && fname.toLowerCase().endsWith(".xml")) {
				fname = fname.substring(0, fname.length() - 4) + format;
			}

			// Writes response
			if (format.equals("pdf")) {
				writePdf(fname, w, h, bg, xml, response);
			} else {
				writeImage(format, fname, w, h, bg, xml, response,request);
			}

			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	/**
	 * Gets the XML request parameter.
	 */
	protected String getRequestXml(HttpServletRequest request)
			throws IOException, UnsupportedEncodingException {
		String enc = request.getParameter("xml");
		String xml = null;

		if (enc != null && enc.length() > 0) {
			xml = Utils.inflate(mxBase64
					.decode(URLDecoder.decode(enc, "UTF-8")));
		} else {
			xml = URLDecoder.decode(request.getParameter("plain"), "UTF-8");
		}

		return xml;
	}

	/**
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * 
	 */
	protected void writeImage(String format, String fname, int w, int h,
			Color bg, String xml, HttpServletResponse response,HttpServletRequest request)
			throws IOException, SAXException, ParserConfigurationException {
		if (fname != null) {
			response.setContentType("application/octet-stream");
			if(request.getHeader( "USER-AGENT" ).toLowerCase().indexOf( "msie" ) >  0){
				response.setHeader("Content-Disposition",
						"attachment; filename=" + new String(fname.getBytes("gbk") , "ISO8859-1"));
			}else{
				response.setHeader("Content-Disposition",
						"attachment; filename=" + new String(fname.getBytes() , "ISO8859-1"));
			}
			 
		} else if (format != null) {
			response.setContentType("image/" + format.toLowerCase());
		}

		CreateImage.getInstance().createImage(w, h, bg, format, response.getOutputStream(), xml);
	}

	/**
	 * Creates and returns the canvas for rendering.
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	protected void writePdf(String fname, int w, int h, Color bg, String xml,
			HttpServletResponse response) throws DocumentException,
			IOException, SAXException, ParserConfigurationException {
		response.setContentType("application/pdf");

		if (fname != null) {
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fname + "\"");
		}

		// Fixes PDF offset
		w += 1;
		h += 1;

		Document document = new Document(new Rectangle(w, h));
		PdfWriter writer = PdfWriter.getInstance(document, response
				.getOutputStream());
		document.open();

		PdfContentByte pcb = writer.getDirectContent();

		FontMapper fontMapper = new FontMapper() {
			public BaseFont awtToPdf(Font font) {
				try {
					// FIXME written specifically for a modern mac
					return BaseFont.createFont(
							"Unicode.ttf",
							BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			public Font pdfToAwt(BaseFont font, int size) {
				return null;
			}
		};

		Graphics2D g2d = pcb.createGraphics(w, h, fontMapper);
		mxGraphicsCanvas2D gc = createCanvas(g2d);

		// Fixes PDF offset
		gc.translate(1, 1);

		renderXml(xml, gc);
		gc.getGraphics().dispose();
		document.close();
		writer.flush();
		writer.close();
	}

	/**
	 * Renders the XML to the given canvas.
	 */
	protected void renderXml(String xml, mxICanvas2D canvas)
			throws SAXException, ParserConfigurationException, IOException {
		XMLReader reader = parserFactory.newSAXParser().getXMLReader();
		reader.setContentHandler(new mxSaxOutputHandler(canvas));
		reader.parse(new InputSource(new StringReader(xml)));
	}

	/**
	 * Creates a graphics canvas with an image cache.
	 */
	protected mxGraphicsCanvas2D createCanvas(Graphics2D g2) {
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
