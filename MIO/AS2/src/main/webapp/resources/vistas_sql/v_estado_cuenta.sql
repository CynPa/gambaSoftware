create or replace view v_estado_cuenta as

	select fc.id_sucursal, fc.id_zona, em.id_empresa, em.nombre_fiscal, em.codigo, em.identificacion, fc.numero as numero_factura, fc.fecha as fecha_factura,
	fc.id_factura_cliente, fc.descripcion as descripcion_documento, case when cxc.indicador_generada_protesto = true then cxc.fecha_protesto else fc.fecha end as fecha_documento, fc.numero as numero_documento, 
	d.codigo as codigo_documento, d.nombre as nombre_documento,cxc.id_cuenta_por_cobrar, cxc.fecha_vencimiento as fecha_vence, cxc.valor as debito, 0 as credito,
	cl.id_recaudador, tr.nombre as nombre_recaudador,concat(u.nombre2,' ',u.nombre1) as nombre_agente_comercial,fc.id_organizacion as id_organizacion, 
	se.id_subempresa,se.empresa_final,eh.identificacion as identificacion_subempresa, eh.nombre_fiscal as nombre_fiscal_subempresa,
	cxc.indicador_generada_protesto AS indicador_generada_protesto, fc.id_agente_comercial, cxc.descripcion as cheque_protestado, d.codigo as codigo_documento_proceso,
	cxc.valor_bloqueado, a.numero as asiento_venta, null as asiento_documento, fc.referencia1 as referencia1, fc.referencia2 as referencia2,fc.referencia3 as referencia3, fc.referencia4 as referencia4, 
	fc.referencia5 as referencia5, fc.referencia6 as referencia6, fc.valor_referencia1 as valor_referencia1, fc.valor_referencia2 as valor_referencia2, fc.valor_referencia3 as valor_referencia3,
	ti.codigo codigo_tipo_identificacion, cpago.dias_plazo dias_plazo_factura, fc.fecha_vencimiento fecha_vencimiento_factura, em.tipo_empresa tipo_empresa, oi.codigo origen_ingresos_codigo, d.documento_base documento_base,
	null as id_cobro
	from empresa em 
	inner join factura_cliente fc on fc.id_empresa = em.id_empresa
	left outer join usuario u on fc.id_agente_comercial = u.id_usuario
	inner join documento d on d.id_documento = fc.id_documento
	inner join cliente cl on em.id_empresa = cl.id_empresa
	inner join cuenta_por_cobrar cxc on cxc.id_factura_cliente = fc.id_factura_cliente 
	left outer join recaudador tr on tr.id_recaudador = cl.id_recaudador
	left outer join subempresa se on fc.id_subempresa = se.id_subempresa	
	left outer join empresa eh on se.id_empresa = eh.id_empresa	
	left outer join tipo_identificacion ti on em.id_tipo_identificacion = ti.id_tipo_identificacion
	left outer join condicion_pago cpago on fc.id_condicion_pago = cpago.id_condicion_pago
	left outer join interfaz_contable_proceso icp on icp.id_interfaz_contable_proceso =  fc.id_interfaz_contable_proceso
	left outer join asiento a on a.id_asiento = icp.id_asiento
	left outer join origen_ingresos oi on oi.id_origen_ingresos = em.id_origen_ingresos
	where fc.estado>0 and cxc.indicador_anulada = false union
	select fc.id_sucursal, fc.id_zona, em.id_empresa, em.nombre_fiscal, em.codigo, em.identificacion, fc.numero as numero_factura, fc.fecha as fecha_factura,
	fc.id_factura_cliente, c.descripcion as descripcion_documento, c.fecha as fecha_documento, c.numero as numero_documento,
	df.codigo as codigo_documento, d.nombre as nombre_documento,cxc.id_cuenta_por_cobrar, cxc.fecha_vencimiento as fecha_vence, 0 as debito, dc.valor as credito,
	cl.id_recaudador, tr.nombre as nombre_recaudador,concat(u.nombre2,' ',u.nombre1) as nombre_agente_comercial,fc.id_organizacion as id_organizacion,
	se.id_subempresa,se.empresa_final,eh.identificacion as identificacion_subempresa, eh.nombre_fiscal as nombre_fiscal_subempresa,
	cxc.indicador_generada_protesto AS indicador_generada_protesto, fc.id_agente_comercial, cxc.descripcion as cheque_protestado, d.codigo as codigo_documento_proceso,
	0 valor_bloqueado, null as asiento_venta, a.numero as asiento_documento, fc.referencia1 as referencia1, fc.referencia2 as referencia2, fc.referencia3 as referencia3, fc.referencia4 as referencia4, 
	fc.referencia5 as referencia5, fc.referencia6 as referencia6,fc.valor_referencia1 valor_referencia1, fc.valor_referencia2 as valor_referencia2, fc.valor_referencia3 as valor_referencia3,
	ti.codigo codigo_tipo_identificacion, cpago.dias_plazo dias_plazo_factura, fc.fecha_vencimiento fecha_vencimiento_factura, em.tipo_empresa tipo_empresa, oi.codigo origen_ingresos_codigo, d.documento_base documento_base,
	c.id_cobro id_cobro
	from empresa em
	inner join factura_cliente fc on fc.id_empresa = em.id_empresa
	inner join documento df on fc.id_documento = df.id_documento
	left outer join usuario u on fc.id_agente_comercial = u.id_usuario
	inner join cuenta_por_cobrar cxc on cxc.id_factura_cliente = fc.id_factura_cliente
	inner join detalle_cobro dc on dc.id_cuenta_por_cobrar = cxc.id_cuenta_por_cobrar
	inner join cobro c on c.id_cobro = dc.id_cobro
	inner join documento d on d.id_documento = c.id_documento 
	inner join cliente cl on em.id_empresa = cl.id_empresa
	left outer join recaudador tr on tr.id_recaudador = cl.id_recaudador
	left outer join subempresa se on fc.id_subempresa = se.id_subempresa	
	left outer join empresa eh on se.id_empresa = eh.id_empresa	
	left outer join asiento a on a.id_asiento = c.id_asiento
	left outer join tipo_identificacion ti on em.id_tipo_identificacion = ti.id_tipo_identificacion
	left outer join condicion_pago cpago on fc.id_condicion_pago = cpago.id_condicion_pago
	left outer join origen_ingresos oi on oi.id_origen_ingresos = em.id_origen_ingresos
	where case when d.indicador_contabilizar = false and c.estado <> 0 then 4 else c.estado end >=4 
	union
	select fc.id_sucursal, fc.id_zona, em.id_empresa, em.nombre_fiscal, em.codigo, em.identificacion, fc.numero as numero_factura, fc.fecha as fecha_factura,
	fc.id_factura_cliente, case when nc.id_factura_cliente is not null then concat(doc.nombre,' ',nc.numero,' ',coalesce(nc.descripcion,'')) ELSE ac.descripcion END as descripcion_documento, 
	lac.fecha as fecha_documento, case when nc.id_factura_cliente is not null then concat(nc.numero,'/',lac.numero,'/',ac.numero) else concat(lac.numero,'/',ac.numero) end as numero_documento,
	df.codigo as codigo_documento, d.nombre as nombre_documento, cxc.id_cuenta_por_cobrar, cxc.fecha_vencimiento as fecha_vence, 0 as debito, dlac.valor as credito,
	cl.id_recaudador, tr.nombre as nombre_recaudador,concat(u.nombre2,' ',u.nombre1) as nombre_agente_comercial,fc.id_organizacion as id_organizacion,
	se.id_subempresa,se.empresa_final,eh.identificacion as identificacion_subempresa, eh.nombre_fiscal as nombre_fiscal_subempresa,
	cxc.indicador_generada_protesto AS indicador_generada_protesto, fc.id_agente_comercial, cxc.descripcion as cheque_protestado, d.codigo as codigo_documento_proceso,
	0 valor_bloqueado, null as asiento_venta, a.numero as asiento_documento, fc.referencia1 as referencia1, fc.referencia2 as referencia2, fc.referencia3 as referencia3, fc.referencia4 as referencia4, 
	fc.referencia5 as referencia5, fc.referencia6 as referencia6, fc.valor_referencia1 as valor_referencia1, fc.valor_referencia2 as valor_referencia2, fc.valor_referencia3 as valor_referencia3,
	ti.codigo codigo_tipo_identificacion, cpago.dias_plazo dias_plazo_factura, fc.fecha_vencimiento fecha_vencimiento_factura, em.tipo_empresa tipo_empresa, oi.codigo origen_ingresos_codigo, d.documento_base documento_base,
	null
	from empresa em
	inner join factura_cliente fc on fc.id_empresa = em.id_empresa
	inner join documento df on fc.id_documento = df.id_documento
	left outer join usuario u on fc.id_agente_comercial = u.id_usuario
	inner join cuenta_por_cobrar cxc on cxc.id_factura_cliente = fc.id_factura_cliente
	inner join detalle_liquidacion_anticipo_cliente dlac on dlac.id_cuenta_por_cobrar = cxc.id_cuenta_por_cobrar
	inner join liquidacion_anticipo_cliente lac on lac.id_liquidacion_anticipo_cliente = dlac.id_liquidacion_anticipo_cliente
	inner join anticipo_cliente ac on ac.id_anticipo_cliente = lac.id_anticipo_cliente
	LEFT OUTER JOIN FACTURA_CLIENTE NC ON NC.ID_FACTURA_CLIENTE = ac.id_factura_cliente
	LEFT OUTER JOIN DOCUMENTO doc ON NC.ID_DOCUMENTO = doc.id_DOCUMENTO
	inner join documento d on d.id_documento = lac.id_documento
	inner join cliente cl on em.id_empresa = cl.id_empresa
	left outer join recaudador tr on tr.id_recaudador = cl.id_recaudador
	left outer join subempresa se on fc.id_subempresa = se.id_subempresa	
	left outer join empresa eh on se.id_empresa = eh.id_empresa	
	left outer join asiento a on a.id_asiento = lac.id_asiento
	left outer join tipo_identificacion ti on em.id_tipo_identificacion = ti.id_tipo_identificacion
	left outer join condicion_pago cpago on fc.id_condicion_pago = cpago.id_condicion_pago
	left outer join origen_ingresos oi on oi.id_origen_ingresos = em.id_origen_ingresos
	where case when d.indicador_contabilizar = false and lac.estado <> 0 then 4 else lac.estado end >=4