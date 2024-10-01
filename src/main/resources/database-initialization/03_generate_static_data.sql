-- PROCEDURE: public.generatedata()

-- DROP PROCEDURE IF EXISTS public.generatedata();

CREATE OR REPLACE PROCEDURE public.generatedata(
)
    LANGUAGE 'plpgsql'
AS
$BODY$
declare
-- variable declaration
begin
    --

    DELETE from region_neighbours cascade;
    DELETE from factions cascade;
    DELETE from regions cascade;
    DELETE from production_sites cascade;
    DELETE from unit_types cascade;


end;
$BODY$;

ALTER PROCEDURE public.generatedata()
    OWNER TO postgres;
