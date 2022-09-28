package com.ksoichiro.mcmod.fancyicecream.logging;

import org.apache.logging.log4j.util.StackLocatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
    public static Logger getLogger() {
        return LoggerFactory.getLogger(StackLocatorUtil.getCallerClass(2));
    }
}
