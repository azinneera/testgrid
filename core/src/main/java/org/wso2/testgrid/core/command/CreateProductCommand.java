/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.wso2.testgrid.core.command;

import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.testgrid.common.Product;
import org.wso2.testgrid.common.exception.CommandExecutionException;
import org.wso2.testgrid.common.util.StringUtil;
import org.wso2.testgrid.dao.TestGridDAOException;
import org.wso2.testgrid.dao.uow.ProductUOW;

/**
 * This creates a product test plan from the input arguments and persist the information in a database.
 *
 * @since 1.0.0
 */
public class CreateProductCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(CreateProductCommand.class);

    @Option(name = "--product",
            usage = "Product Name",
            aliases = {"-p"},
            required = true)
    private String productName = "";

    @Option(name = "--version",
            usage = "product version",
            aliases = {"-v"},
            required = true)
    private String productVersion = "";

    @Option(name = "--channel",
            usage = "product channel",
            aliases = {"-c"})
    private String channel = "LTS";

    @Override
    public void execute() throws CommandExecutionException {
        try {
            ProductUOW productUOW = new ProductUOW();
            logger.info("Creating product test plan...");
            logger.info(
                    "Input Arguments: \n" +
                    "\tProduct name: " + productName + "\n" +
                    "\tProduct version: " + productVersion + "\n" +
                    "\tChannel" + channel);
            /*
                psuedo code:
                query db: is product test plan for the given product/version/channel exist
                if true:
                    logger Product information already stored in the db
                if false:
                    persist p/v/c into the db
             */

            Product product = createProduct(productName, productVersion, channel);
            productUOW.persistProduct(product);
        } catch (TestGridDAOException e) {
            throw new CommandExecutionException("Error occurred while persisting Product", e);
        }
    }

    /**
     * Creates an instance of {@link Product} for the given parameters.
     *
     * @param productName    product name
     * @param productVersion product version
     * @param channel        product test plan channel
     * @return {@link Product} instance for the given parameters
     * @throws CommandExecutionException thrown when error on creating an instance of {@link Product}
     */
    private Product createProduct(String productName, String productVersion, String channel)
            throws CommandExecutionException {
        try {
            Product.Channel productTestPlanChannel = Product.Channel.valueOf(channel);
            Product productTestPlan = new Product();

            productTestPlan.setProductName(productName);
            productTestPlan.setProductVersion(productVersion);
            productTestPlan.setChannel(productTestPlanChannel);
            return productTestPlan;
        } catch (IllegalArgumentException e) {
            throw new CommandExecutionException(StringUtil.concatStrings("Channel ",
                    channel, " is not defined in the available channels enum"), e);
        }
    }
}
