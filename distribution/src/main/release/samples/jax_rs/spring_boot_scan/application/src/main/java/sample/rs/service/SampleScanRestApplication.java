/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package sample.rs.service;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class SampleScanRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleScanRestApplication.class, args);
    }
 
    @Bean
    public Swagger2Feature swaggerFeature(ApplicationContext context) {
        return new Swagger2Feature();
    }
    @Bean
    @ExportMetricWriter
    public MetricWriter metricWriter() {
            return new MetricWriter() {

                @Override
                public void set(Metric<?> arg0) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void increment(Delta<?> arg0) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void reset(String arg0) {
                    // TODO Auto-generated method stub
                    
                }
                
            };
    }
}