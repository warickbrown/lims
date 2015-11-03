/* http://docs.spring.io/spring-security/site/docs/3.0.x/reference/appendix-schema.html */
DROP TABLE IF EXISTS person CASCADE;
CREATE TABLE person (
    person_id SERIAL NOT NULL PRIMARY KEY,
    username character varying(50) NOT NULL,
    password character(60) NOT NULL,
    enabled boolean NOT NULL,
    constraint ix_person_username_unique unique (username)
);
COMMENT ON TABLE person IS 'Users and credentials.';

DROP TABLE IF EXISTS authority CASCADE;
CREATE TABLE authority (
    authority_id SERIAL NOT NULL PRIMARY KEY,
    authority_name character varying(50) NOT NULL,
    constraint ix_authority_authority_name_unique unique (authority_name)
);
COMMENT ON TABLE authority IS 'e.g. ROLE_USER, ROLE_ADMIN.';

DROP TABLE IF EXISTS person_authority CASCADE;
DROP TABLE IF EXISTS person_authority_mm CASCADE;
CREATE TABLE person_authority_mm (
    person_id integer NOT NULL REFERENCES person,
    authority_id integer NOT NULL REFERENCES authority,
    constraint ix_person_authority_mm_person_id_and_authority_id_unique unique (person_id, authority_id)
);
COMMENT ON TABLE person_authority_mm IS 'Defines what authorities people have.';

DROP TABLE IF EXISTS faction CASCADE;
CREATE TABLE faction (
    faction_id SERIAL NOT NULL PRIMARY KEY,
  	faction_name character varying(50) NOT NULL,
    constraint ix_faction_faction_name_unique unique (faction_name)
);
COMMENT ON TABLE faction IS 'e.g. FACTION_EMPLOYEE, FACTION_SENIOR_EMPLOYEE.';

DROP TABLE IF EXISTS faction_authority CASCADE;
DROP TABLE IF EXISTS faction_authority_mm CASCADE;
CREATE TABLE faction_authority_mm (
  	faction_id integer NOT NULL REFERENCES faction,
    authority_id integer NOT NULL REFERENCES authority,
    constraint ix_faction_authority_mm_faction_id_and_authority_id_unique unique (faction_id, authority_id)
);
COMMENT ON TABLE faction_authority_mm IS 'Defines what factions people belong to.';
    
DROP TABLE IF EXISTS faction_person CASCADE;
DROP TABLE IF EXISTS faction_person_mm CASCADE;
CREATE TABLE faction_person_mm (
    faction_id integer NOT NULL REFERENCES faction,
    person_id integer NOT NULL REFERENCES person,
  	constraint ix_faction_person_mm_faction_id_and_person_id_unique unique (faction_id, person_id)
);
COMMENT ON TABLE faction_person_mm IS 'Groups people into factions.';

DROP TABLE IF EXISTS manufacturer CASCADE;
CREATE TABLE manufacturer (
  	manufacturer_id SERIAL NOT NULL PRIMARY KEY,
    manufacturer_name character varying(64) NOT NULL,
  	constraint ix_manufacturer_manufacturer_name_unique unique (manufacturer_name)
);
COMMENT ON TABLE manufacturer IS 'Manufacturers of instruments.';

DROP TABLE IF EXISTS instrument CASCADE;
CREATE TABLE instrument (
  	instrument_id SERIAL NOT NULL PRIMARY KEY,
  	manufacturer_id integer NOT NULL REFERENCES manufacturer,
    instrument_name character varying(64) NOT NULL,
  	constraint ix_instrument_manufacturer_id_instrument_name_unique unique (manufacturer_id, instrument_name)
);
COMMENT ON TABLE instrument IS 'Instrument names linked to a specific manufacturer.';

DROP TABLE IF EXISTS analysis_type CASCADE;
CREATE TABLE analysis_type (
  	analysis_type_id SERIAL NOT NULL PRIMARY KEY,
    analysis_type_name character varying(64) NOT NULL,
  	constraint ix_analysis_type_analysis_type_name_unique unique (analysis_type_name)
);
COMMENT ON TABLE analysis_type IS 'Broad type of analysis, e.g. Mineral classification.';

DROP TABLE IF EXISTS result_provision_type CASCADE;
	CREATE TABLE result_provision_type (
	result_provision_type_id SERIAL NOT NULL PRIMARY KEY,
	result_provision_type_name character varying(64) NOT NULL,
	constraint ix_result_provision_type_result_provision_type_name_unique unique (result_provision_type_name)
);
COMMENT ON TABLE result_provision_type IS 'Ways in which results can be povided, e.g. File Upload, User Entry.';

DROP TABLE IF EXISTS result_processor_type CASCADE;
	CREATE TABLE result_processor_type (
	result_processor_type_id SERIAL NOT NULL PRIMARY KEY,
	result_processor_type_name character varying(64) NOT NULL,
	constraint ix_result_processor_type_result_processor_type_name_unique unique (result_processor_type_name)
);
COMMENT ON TABLE result_processor_type IS 'Ways in which results can be processed, e.g. External, Class.';

DROP TABLE IF EXISTS result_type CASCADE;
	CREATE TABLE result_type (
	result_type_id SERIAL NOT NULL PRIMARY KEY,
	result_type_name character varying(64) NOT NULL,
	constraint ix_result_type_result_type_name_unique unique (result_type_name)
);
COMMENT ON TABLE result_type IS 'Data type of end result, e.g. String, Float, URL, File.';

DROP TABLE IF EXISTS result_processor CASCADE;
DROP TABLE IF EXISTS analysis_capability CASCADE;
CREATE TABLE analysis_capability (
  	analysis_capability_id SERIAL NOT NULL PRIMARY KEY,
  	instrument_id integer NOT NULL REFERENCES instrument,
    analysis_type_id integer NOT NULL REFERENCES analysis_type,
  	result_processor_type_id integer REFERENCES result_processor_type,
  	result_provision_type_id integer NOT NULL REFERENCES result_provision_type,
  	result_type_id integer NOT NULL REFERENCES result_type,
  	result_processor_name character varying(64) NOT NULL,
    version character varying(16) NOT NULL,
    result_processor_value character varying(64)
    /*constraint ix_analysis_capability_result_processor_name_version_unique unique (result_processor_name, version),
    constraint ix_analysis_capability_instrument_id_analysis_type_id_result_processor_id_unique unique (instrument_id, analysis_type_id, result_processor_id)*/
);
COMMENT ON TABLE analysis_capability IS 'Defines an analysis capability of an instrument and describes how its results should be processed.';

DROP TABLE IF EXISTS analysis_request_state CASCADE;
CREATE TABLE analysis_request_state (
  	analysis_request_state_id SERIAL NOT NULL PRIMARY KEY,
    analysis_request_state_name character varying(64) NOT NULL,
	constraint ix_analysis_request_state_analysis_request_state_name_unique unique (analysis_request_state_name)
);
COMMENT ON TABLE analysis_request_state IS 'Describes what state an analysis is currently in.';

DROP TABLE IF EXISTS analysis_request CASCADE;
CREATE TABLE analysis_request (
  	analysis_request_id SERIAL NOT NULL PRIMARY KEY,
  	analysis_request_state_id integer NOT NULL REFERENCES analysis_request_state,
    analysis_capability_id integer NOT NULL REFERENCES analysis_capability,
    person_id integer NOT NULL REFERENCES person,
    request_date timestamp NOT NULL
);
COMMENT ON TABLE analysis_request IS 'Describes when an analysis capability was requested and by whom.';

DROP TABLE IF EXISTS mount_type CASCADE;
	CREATE TABLE mount_type (
	mount_type_id SERIAL NOT NULL PRIMARY KEY,
	mount_type_name character varying(64) NOT NULL,
	constraint ix_mount_type_mount_type_name_unique unique (mount_type_name)
);
COMMENT ON TABLE mount_type IS 'Way in which the sample was mounted, e.g. None, Epoxy Resin 25mm Round.';

DROP TABLE IF EXISTS geosample CASCADE;
CREATE TABLE geosample (
	geosample_id SERIAL NOT NULL PRIMARY KEY,
	mount_type_id integer NOT NULL REFERENCES mount_type,
	igsn character(9),
	geosample_name character varying(32),
	location geometry(Point, 4326) NOT NULL,
	rock_type character varying(32),
	site_code character varying(32),
	tectonic_unit character varying(128),
	constraint ix_geosample_igsn_unique unique (igsn)
);
COMMENT ON TABLE geosample IS 'Describes an individual geological sample, including its location and metadata.';

DROP TABLE IF EXISTS analysis_request_geosample CASCADE;
DROP TABLE IF EXISTS analysis_request_geosample_mm CASCADE;
CREATE TABLE analysis_request_geosample_mm (
	analysis_request_id integer NOT NULL REFERENCES analysis_request,
	geosample_id integer NOT NULL REFERENCES geosample,
	constraint ix_analysis_request_geosample_mm_analysis_request_id_geosample_id_unique unique (analysis_request_id, geosample_id)
);
COMMENT ON TABLE analysis_request_geosample_mm IS 'Links geosamples to be included in an analysis request.';

/* TODO: I think the result table should be changed to point directly to an analysis_request_geosample_mm instead of having
 * both the analysis request ids and geosample ids as separate fks. Not sure how Hibernate will handle this though... */
DROP TABLE IF EXISTS result CASCADE;
CREATE TABLE result (
	result_id SERIAL NOT NULL PRIMARY KEY,
	analysis_request_id integer NOT NULL REFERENCES analysis_request,
	geosample_id integer NOT NULL REFERENCES geosample,	
	value character varying(1024),
	has_staged_file boolean NOT NULL,
	constraint ix_result_analysis_request_id_geosample_id_unique unique (analysis_request_id, geosample_id)
);
COMMENT ON TABLE result IS 'Associates a result value with an analysis request and a geosample.';

DROP TABLE IF EXISTS mineral CASCADE;
CREATE TABLE mineral (
    mineral_id SERIAL NOT NULL PRIMARY KEY,
    mineral_name character varying(32) NOT NULL,
    constraint ix_mineral_mineral_name_unique unique (mineral_name)
);

DROP TABLE IF EXISTS mindif CASCADE;
CREATE TABLE mindif (
    mindif_id SERIAL NOT NULL PRIMARY KEY,
    geosample_id int NOT NULL REFERENCES geosample,
    measurement_guid character(32) NOT NULL,
    software_version character varying(16) NOT NULL,
    view_field_um integer NOT NULL,
    image_width_px integer NOT NULL,
    image_height_px integer NOT NULL,
    sample_diameter_um integer NOT NULL,
    collection_date timestamp NOT NULL,
    constraint ix_mindif_measurement_guid_unique unique (measurement_guid)
);

DROP TABLE IF EXISTS mindif_field CASCADE;
CREATE TABLE mindif_field (
    mindif_field_id SERIAL NOT NULL PRIMARY KEY,
    mindif_id integer NOT NULL REFERENCES mindif,
    mindif_field_name character varying(4) NOT NULL,
    position_x_px integer NOT NULL,
    position_y_px integer NOT NULL,
    constraint ix_mindif_field_mindif_id_and_mindif_field_name_unique unique (mindif_id, mindif_field_name)
);

DROP TABLE IF EXISTS mineral_occurrence CASCADE;
CREATE TABLE mineral_occurrence (
    mineral_occurrence_id SERIAL NOT NULL PRIMARY KEY,
    mindif_field_id integer NOT NULL REFERENCES mindif_field,
    mineral_id integer NOT NULL REFERENCES mineral,    
    mineral_pixel_count integer NOT NULL,
    constraint ix_mineral_occurrence_mindif_field_id_and_mineral_id_unique unique (mindif_field_id, mineral_id)
);

DROP TABLE IF EXISTS mineral_mass_and_colour CASCADE;
CREATE TABLE mineral_mass_and_colour (
    mineral_mass_and_colour_id SERIAL NOT NULL PRIMARY KEY,
    mindif_id integer NOT NULL REFERENCES mindif,
    mineral_id integer NOT NULL REFERENCES mineral,    
    colour bit(24) NOT NULL,
    mass double precision NOT NULL,
    constraint ix_mineral_mass_mindif_id_unique_and_colour_mineral_id unique (mindif_id, mineral_id)
);

DROP VIEW IF EXISTS view_geosample_and_mineral_count;
CREATE VIEW view_geosample_and_mineral_count AS
SELECT      location,
			geosample.igsn,
			'http://hdl.handle.net/10273/' || geosample.igsn AS igsn_url,
            geosample_name as name,
            mount_type.mount_type_name,
			TEXT 'GSWA' as sample_custodian,
            TEXT 'http://www.dmp.wa.gov.au/371.aspx' as sample_custodian_url,            
            TEXT 'http://dx.doi.org/10.4225/06/553721D2E190D' as collection_url,
            TEXT 'TESCAN Integrated Mineral Analyser' as instrument_name, /* TODO: this could be more dynamic but we need a better DB schema. */
            software_version,
            result.value as data_url,
            'http://ddfe.curtin.edu.au/gswa-library/' || result.analysis_request_id || '/' || geosample.igsn || '/' || geosample.igsn || '.classification.png' as image_url,
            'http://ddfe.curtin.edu.au/gswa-library/' || result.analysis_request_id || '/' || geosample.igsn || '/' || geosample.igsn || '.classification.thumbnail.png' as image_thumbnail_url,
            collection_date as analysis_date,
            TEXT 'John de Laeter Centre' as analysed_by,
            TEXT 'http://jdlc.curtin.edu.au/' as analysed_by_url,
            (
                SELECT CONCAT('{', ARRAY_TO_STRING(ARRAY
                (
                    SELECT      CONCAT(TO_JSON(mineral.mineral_name),
                                    ': {"mineral_pixel_count": ', SUM(mineral_occurrence.mineral_pixel_count), 
                                    ', "colour": "#' || LPAD(to_hex(colour::int), 6, '0'), 
                                    '", "mass": ', mass, '}')
                    FROM        mineral_occurrence
                    JOIN        mineral         ON mineral.mineral_id = mineral_occurrence.mineral_id
                    JOIN        mindif_field    ON mindif_field.mindif_field_id = mineral_occurrence.mindif_field_id
                    JOIN        mindif          ON mindif.mindif_id = mindif_field.mindif_id
                    JOIN        mineral_mass_and_colour ON mineral_mass_and_colour.mindif_id = mindif.mindif_id AND mineral_mass_and_colour.mineral_id = mineral.mineral_id
                    WHERE       mindif.geosample_id = geosample.geosample_id
                    GROUP BY    mineral.mineral_id, colour, mass
                ), ','), '}')
            ) AS mineral_information_json
FROM        result
JOIN        geosample ON geosample.geosample_id = result.geosample_id
JOIN        mindif ON mindif.geosample_id = geosample.geosample_id
JOIN		mount_type ON mount_type.mount_type_id = geosample.mount_type_id;;


DROP TABLE IF EXISTS gt_pk_metadata_table CASCADE;
CREATE TABLE gt_pk_metadata_table (
    table_schema VARCHAR(32) NOT NULL,
    table_name VARCHAR(32) NOT NULL,
    pk_column VARCHAR(32) NOT NULL,
    pk_column_idx INTEGER,
    pk_policy VARCHAR(32),
    pk_sequence VARCHAR(64),
    unique (table_schema, table_name, pk_column),
    check (pk_policy in ('sequence', 'assigned', 'autoincrement'))
);

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO curtin_lims_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public to curtin_lims_user;