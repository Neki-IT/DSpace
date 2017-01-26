package org.dspace.app.rest.converter;

import java.sql.SQLException;
import java.util.List;

import org.dspace.app.rest.model.BitstreamFormatRest;
import org.dspace.app.rest.model.BitstreamRest;
import org.dspace.app.rest.model.CheckSum;
import org.dspace.content.Bitstream;
import org.dspace.content.BitstreamFormat;
import org.dspace.content.Bundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BitstreamConverter
		extends DSpaceObjectConverter<org.dspace.content.Bitstream, org.dspace.app.rest.model.BitstreamRest> {
	@Autowired
	BitstreamFormatConverter bfConverter;
	
	@Override
	public org.dspace.content.Bitstream toModel(org.dspace.app.rest.model.BitstreamRest obj) {
		return super.toModel(obj);
	}

	@Override
	public BitstreamRest fromModel(org.dspace.content.Bitstream obj) {
		BitstreamRest b = super.fromModel(obj);
		List<Bundle> bundles = null;
		try {
			bundles = obj.getBundles();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (bundles != null && bundles.size() > 0) {
			b.setBundleName(bundles.get(0).getName());
		}
		CheckSum checksum = new CheckSum();
		checksum.setCheckSumAlgorithm(obj.getChecksumAlgorithm());
		checksum.setValue(obj.getChecksum());
		b.setCheckSum(checksum);
		BitstreamFormatRest format = null;
		try {
			format = bfConverter.fromModel(obj.getFormat(null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		b.setFormat(format);
		b.setSizeBytes(obj.getSize());
		return b;
	}
	
	@Override
	protected BitstreamRest newInstance() {
		return new BitstreamRest();
	}
}
