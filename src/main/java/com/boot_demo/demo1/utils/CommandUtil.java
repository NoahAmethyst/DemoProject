package com.boot_demo.demo1.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Component
public class CommandUtil {

    public  String command(List<String> cmds) throws IOException {
        Process ps = Runtime.getRuntime().exec(cmds.toArray(new String[cmds.size()]));
        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        BufferedReader ebr = new BufferedReader(new InputStreamReader(ps.getErrorStream()));
        StringBuffer sb = new StringBuffer();
        StringBuffer esb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        while ((line = ebr.readLine()) != null) {
            esb.append(line);
        }

        String result = sb.toString();
        ps.destroy();
        if (StringUtils.isNotEmpty(result)) {
            return result;
        } else {
            return esb.toString();
        }
    }
}
