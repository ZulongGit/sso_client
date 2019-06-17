
package com.krm.web.util.config;

import java.util.Date;
import java.util.Properties;

public abstract interface ConfigDao {
	public abstract Properties queryConfig(Date paramDate);
}