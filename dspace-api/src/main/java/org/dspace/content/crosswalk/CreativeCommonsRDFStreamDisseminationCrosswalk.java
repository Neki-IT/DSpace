/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.content.crosswalk;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Bitstream;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.dspace.content.factory.ContentServiceFactory;
import org.dspace.content.service.BitstreamService;
import org.dspace.core.Constants;
import org.dspace.core.Context;
import org.dspace.core.Utils;
import org.dspace.license.factory.LicenseServiceFactory;
import org.dspace.license.service.CreativeCommonsService;

/**
 * Export the item's Creative Commons license, RDF form.
 *
 * @author Larry Stone
 * @version $Revision: 1.0 $
 */
public class CreativeCommonsRDFStreamDisseminationCrosswalk
    implements StreamDisseminationCrosswalk {
    /**
     * log4j logger
     */
    private static Logger log =
            org.apache.logging.log4j.LogManager.getLogger(CreativeCommonsRDFStreamDisseminationCrosswalk.class);
    protected BitstreamService bitstreamService = ContentServiceFactory.getInstance().getBitstreamService();
    protected CreativeCommonsService creativeCommonsService = LicenseServiceFactory.getInstance()
                                                                                   .getCreativeCommonsService();

    @Override
    public boolean canDisseminate(Context context, DSpaceObject dso) {
        try {
            return dso.getType() == Constants.ITEM &&
                creativeCommonsService.getLicenseRdfBitstream((Item) dso) != null;
        } catch (Exception e) {
            log.error("Failed getting CC license", e);
            return false;
        }
    }

    @Override
    public void disseminate(Context context, DSpaceObject dso, OutputStream out)
        throws CrosswalkException, IOException, SQLException, AuthorizeException {
        if (dso.getType() == Constants.ITEM) {
            Bitstream cc = creativeCommonsService.getLicenseRdfBitstream((Item) dso);
            if (cc != null) {
                Utils.copy(bitstreamService.retrieve(context, cc), out);
            }
        }
    }

    @Override
    public String getMIMEType() {
        return "text/xml";
    }
}
