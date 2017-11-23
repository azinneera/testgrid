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
 */

package org.wso2.testgrid.common;

import org.wso2.carbon.config.annotation.Element;

import java.io.Serializable;
import java.util.Locale;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Defines a model object of TestScenario with required attributes.
 *
 * @since 1.0.0
 */
@Entity
@Table(name = "test_scenario")
public class TestScenario extends AbstractUUIDEntity implements Serializable {

    private static final long serialVersionUID = -2666342786241472418L;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "name", nullable = false)
    @Element(description = "name of the solution pattern which is covered by this test scenario")
    private String name;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, targetEntity = TestPlan.class)
    @PrimaryKeyJoinColumn(name = "test_plan_id", referencedColumnName = "id")
    private TestPlan testPlan;

    @Transient
    @Element(description = "flag to enable or disable the test scenario")
    private boolean enabled;

    @Transient
    @Element(description = "holds the test engine type (i.e. JMETER, TESTNG)")
    private TestEngine testEngine;

    /**
     * Returns the status of the test scenario.
     *
     * @return test scenario status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the test scenario status.
     *
     * @param status test scenario status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns the name of the test scenario.
     *
     * @return test scenario name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the test scenario name.
     *
     * @param name test scenario name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the test plan associated with this test scenario.
     *
     * @return test plan associated with this test scenario
     */
    public TestPlan getTestPlan() {
        return testPlan;
    }

    /**
     * Sets the test plan associated with this test scenario
     *
     * @param testPlan test plan associated with this test scenario
     */
    public void setTestPlan(TestPlan testPlan) {
        this.testPlan = testPlan;
    }

    /**
     * Returns whether the test scenario is enabled or not.
     * <p>
     * Returns {@code true} if the test scenario is enabled, {@code false} otherwise
     *
     * @return is test scenario enabled or not
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether the test scenario is enabled or not.
     *
     * @param enabled is test scenario enabled or not
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns the test engine in which the test scenario should be executed.
     *
     * @return test scenario executing test engine
     */
    public TestEngine getTestEngine() {
        return testEngine;
    }

    /**
     * Sets the test engine in which the test scenario should be executed.
     *
     * @param testEngine test engine in which the test scenario should be executed
     */
    public void setTestEngine(TestEngine testEngine) {
        this.testEngine = testEngine;
    }

    /**
     * Sets the test engine in which the test scenario should be executed.
     *
     * @param testEngine test engine in which the test scenario should be executed
     */
    public void setTestEngine(String testEngine) {
        this.testEngine = TestEngine.valueOf(testEngine.toUpperCase(Locale.ENGLISH));
    }

    /**
     * This defines the possible statuses of the {@link TestScenario}.
     *
     * @since 1.0.0
     */
    public enum Status {

        /**
         * Planned to execute.
         */
        TEST_SCENARIO_PENDING("TEST_SCENARIO_PENDING"),

        /**
         * Executing in TestEngine.
         */
        TEST_SCENARIO_RUNNING("TEST_SCENARIO_RUNNING"),

        /**
         * Execution completed.
         */
        TEST_SCENARIO_COMPLETED("TEST_SCENARIO_COMPLETED"),

        /**
         * Execution error.
         */
        TEST_SCENARIO_ERROR("TEST_SCENARIO_ERROR");

        private final String status;

        /**
         * Sets the status of the test case.
         *
         * @param status test case status
         */
        Status(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return this.status;
        }
    }

    /**
     * This defines the TestEngines in which the TestScenario can be executed.
     *
     * @since 1.0.0
     */
    public enum TestEngine {

        /**
         * Defines the JMeter TestEngine.
         */
        JMETER("JMETER"),

        /**
         * Defines the TestNg TestEngine.
         */
        TESTNG("TESTNG"),

        /**
         * Defines the Selenium TestEngine.
         */
        SELENIUM("SELENIUM");

        private final String testEngine;

        /**
         * Sets the test engine for the test scenario.
         *
         * @param testEngine test engine for the test scenario
         */
        TestEngine(String testEngine) {
            this.testEngine = testEngine;
        }

        @Override
        public String toString() {
            return this.testEngine;
        }
    }
}