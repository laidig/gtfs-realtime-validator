/*
 * Copyright (C) 2017 University of South Florida.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.usf.cutr.gtfsrtvalidator.api.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.usf.cutr.gtfsrtvalidator.db.GTFSDB;
import edu.usf.cutr.gtfsrtvalidator.hibernate.HibernateUtil;
import junit.framework.TestCase;
import javax.ws.rs.core.Response;

/*
 * Tests loading GTFS data.
 */
public class GtfsFeedTest extends TestCase {

    private final String validGtfsFeedURL = "http://gohart.org/google/google_transit.zip";
    private final String invalidGtfsFeedURL = "DUMMY";
    private final String downloadFailURL = "http://gohart.org/google/file_not_exist.zip";

    GtfsFeed gtfsFeed;

    protected void setUp() {
        gtfsFeed  = new GtfsFeed();
        HibernateUtil.configureSessionFactory();
        GTFSDB.InitializeDB();
    }

    public void testGtfsFeed() throws JsonProcessingException {
        Response response;

        response = gtfsFeed.postGtfsFeed(validGtfsFeedURL);
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

        response = gtfsFeed.postGtfsFeed(invalidGtfsFeedURL);
        assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

        response = gtfsFeed.postGtfsFeed(downloadFailURL);
        assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }
}