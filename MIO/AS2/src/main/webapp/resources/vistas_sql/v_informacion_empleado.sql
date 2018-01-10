create or replace view v_informacion_empleado as(

 select emp.nombres, emp.apellidos, emp.id_empleado id, emp.id_empleado, e.identificacion cedula, u.direccion1 calle_principal, u.direccion3 numero_casa,
 trim(cast('02' as character varying(5))) ciudad, trim(cast('17' as character varying(5))) provincia, d.telefono1 telefono,
 suc.codigo establecimiento, emp.residencia_trabajador, emp.pais_residencia_trabajador pais_residencia,
 emp.convenio_doble_imposicion aplica_convenio, 
 emp.condicion_respecto_discapacidad tipo_trabajador_discapacidad,
 emp.porcentaje_discapacidad,
 case when coalesce(emp.tipo_identificacion_sustituto_pariente,'') = '' then 'N' else emp.tipo_identificacion_sustituto_pariente end tipo_identificacion_discapacidad,
 case when coalesce(emp.identificacion_sustituto_pariente,'') = '' then '999' else emp.identificacion_sustituto_pariente end identificacion_discapacidad,
 ti.codigo tipo_identificacion
 from ubicacion u 
 inner join direccion_empresa d on u.id_ubicacion = d.id_ubicacion
 inner join empresa e on d.id_empresa = e.id_empresa
 inner join empleado emp on e.id_empresa = emp.id_empresa
 inner join sucursal suc on suc.id_sucursal = emp.id_sucursal
 inner join tipo_identificacion ti on e.id_tipo_identificacion = ti.id_tipo_identificacion
 where d.indicador_direccion_principal = true );