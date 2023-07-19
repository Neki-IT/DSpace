--
-- The contents of this file are subject to the license and copyright
-- detailed in the LICENSE and NOTICE files at the root of the source
-- tree and available online at
--
-- http://www.dspace.org/license/
--

-----------------------------------------------------------------------------------
-- Create columns copy_left and copy_right for RelationshipType
-----------------------------------------------------------------------------------

ALTER TABLE relationship_type ADD copy_to_left BOOLEAN DEFAULT FALSE NOT NULL;
ALTER TABLE relationship_type ADD copy_to_right BOOLEAN DEFAULT FALSE NOT NULL;