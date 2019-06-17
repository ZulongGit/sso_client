
package com.krm.common.utils.config;

import java.util.Date;
import java.util.Properties;

public abstract interface ConfigDao {
	public abstract Properties queryConfig(Date paramDate);
}