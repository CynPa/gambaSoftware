create or replace view v_estado_cuenta_proveedor as(
	select fp.id_sucursal, s.nombre as nombre_sucursal, em.id_empresa, em.nombre_fiscal, em.codigo, em.identificacion, coalesce(concat(fps.establecimiento,'-',fps.punto_emision,'-',fps.numero),fp.referencia3) as numero_factura, fp.fecha as fecha_factura, coalesce(fp.fecha_recepcion,fp.fecha) as fecha_recepcion_factura,
	fp.id_factura_proveedor, fp.descripcion as descripcion_factura, fp.descripcion as descripcion_documento, fp.fecha as fecha_documento, fp.numero as numero_documento, 
	d.codigo as codigo_documento, d.nombre as nombre_documento,
	cxp.id_cuenta_por_pagar, cxp.fecha_vencimiento as fecha_vence, cxp.valor as debito, 0 as credito,fp.id_organizacion as id_organizacion,t.id_tipo_operacion,t.nombre as tipo_operacion, 
	d.codigo as codigo_documento_proceso, a.numero as asiento_compra, null as asiento_documento, 0.00 as valor_retenido , fp.referencia1, fp.referencia2, fp.valor_referencia1, fp.valor_referencia2, fp.valor_referencia3,
	catem.id_categoria_empresa as id_categoria_empresa, catem.nombre as nombre_categoria_empresa, d.documento_base documento_base,
	cast(null as int) as id_pago, fps.fecha_emision as fecha_emision
	from empresa em 
	inner join factura_proveedor fp on fp.id_empresa = em.id_empresa
	inner join categoria_empresa catem on em.id_categoria_empresa = catem.id_categoria_empresa
	inner join documento d on d.id_documento = fp.id_documento
	inner join cuenta_por_pagar cxp on cxp.id_factura_proveedor = fp.id_factura_proveedor
	left outer join factura_proveedorSRI fps on fps.id_factura_proveedor = fp.id_factura_proveedor
	left outer join tipo_operacion t on t.id_tipo_operacion = fp.id_tipo_operacion
	left outer join asiento a on a.id_asiento = fp.id_asiento
	left outer join sucursal s on s.id_sucursal = fp.id_sucursal
	where fp.estado>0
	union
	select fp.id_sucursal, s.nombre as nombre_sucursal, em.id_empresa, em.nombre_fiscal, em.codigo, em.identificacion, coalesce(concat(fps.establecimiento,'-',fps.punto_emision,'-',fps.numero),fp.referencia3) as numero_factura, fp.fecha as fecha_factura, coalesce(fp.fecha_recepcion,fp.fecha) as fecha_recepcion_factura,
	fp.id_factura_proveedor, fp.descripcion as descripcion_factura, fp.descripcion as descripcion_documento, fp.fecha as fecha_documento, concat(fp.numero,(' B*')) as numero_documento, 
	d.codigo as codigo_documento, d.nombre as nombre_documento,
	cxp.id_cuenta_por_pagar, cxp.fecha_vencimiento as fecha_vence, 0 as debito, fp.bono as credito,fp.id_organizacion as id_organizacion,t.id_tipo_operacion,t.nombre as tipo_operacion, 
	d.codigo as codigo_documento_proceso, a.numero as asiento_compra, null as asiento_documento, 0.00 as valor_retenido, fp.referencia1, fp.referencia2, fp.valor_referencia1, fp.valor_referencia2, fp.valor_referencia3,
	catem.id_categoria_empresa as id_categoria_empresa, catem.nombre as nombre_categoria_empresa, d.documento_base documento_base, null id_pago,  fps.fecha_emision as fecha_emision
	from empresa em 
	inner join factura_proveedor fp on fp.id_empresa = em.id_empresa
	inner join categoria_empresa catem on em.id_categoria_empresa = catem.id_categoria_empresa
	inner join documento d on d.id_documento = fp.id_documento
	inner join cuenta_por_pagar cxp on cxp.id_factura_proveedor = fp.id_factura_proveedor 
	left outer join factura_proveedorSRI fps on fps.id_factura_proveedor = fp.id_factura_proveedor
	left outer join tipo_operacion t on t.id_tipo_operacion = fp.id_tipo_operacion
	left outer join asiento a on a.id_asiento = fp.id_asiento
	left outer join sucursal s on s.id_sucursal = fp.id_sucursal
	where fp.estado>0 and fp.bono<>0
	union
	select fp.id_sucursal, s.nombre as nombre_sucursal, em.id_empresa, em.nombre_fiscal, em.codigo, em.identificacion, coalesce(concat(fps.establecimiento,'-',fps.punto_emision,'-',fps.numero),fp.referencia3) as numero_factura, fp.fecha as fecha_factura, coalesce(fp.fecha_recepcion,fp.fecha) as fecha_recepcion_factura,
	fp.id_factura_proveedor, fp.descripcion as descripcion_factura, p.descripcion as descripcion_documento, p.fecha as fecha_documento, case when fps.id_pago=p.id_pago then fps.numero_retencion else p.numero end  as numero_documento,
	df.codigo as codigo_documento, d.nombre as nombre_documento,
	cxp.id_cuenta_por_pagar, cxp.fecha_vencimiento as fecha_vence, 0 as debito, dp.valor as credito,fp.id_organizacion as id_organizacion,t.id_tipo_operacion,t.nombre as tipo_operacion,
	d.codigo as codigo_documento_proceso, null as asiento_compra, a.numero as asiento_documento, case when fps.id_pago=p.id_pago then dp.valor else 0.00 end as valor_retenido , fp.referencia1, fp.referencia2, fp.valor_referencia1, fp.valor_referencia2, fp.valor_referencia3,
	catem.id_categoria_empresa as id_categoria_empresa, catem.nombre as nombre_categoria_empresa, d.documento_base documento_base, p.id_pago id_pago,  fps.fecha_emision as fecha_emision
	from empresa em
	inner join factura_proveedor fp on fp.id_empresa = em.id_empresa
	inner join categoria_empresa catem on em.id_categoria_empresa = catem.id_categoria_empresa
	inner join documento df on fp.id_documento = df.id_documento
	inner join cuenta_por_pagar cxp on cxp.id_factura_proveedor = fp.id_factura_proveedor
	inner join detalle_pago dp on dp.id_cuenta_por_pagar = cxp.id_cuenta_por_pagar
	inner join pago p on p.id_pago = dp.id_pago
	inner join documento d on d.id_documento = p.id_documento 
	left outer join factura_proveedorSRI fps on fps.id_factura_proveedor = fp.id_factura_proveedor
	left outer join tipo_operacion t on t.id_tipo_operacion = fp.id_tipo_operacion
	left outer join asiento a on a.id_asiento = p.id_asiento
	left outer join sucursal s on s.id_sucursal = fp.id_sucursal
	where case when d.indicador_contabilizar = false and p.estado <> 0 then 4 else p.estado end >=4
	and p.indicador_retencion_asumida=false
	union
	select fp.id_sucursal, s.nombre as nombre_sucursal, em.id_empresa, em.nombre_fiscal, em.codigo, em.identificacion, coalesce(concat(fps.establecimiento,'-',fps.punto_emision,'-',fps.numero),fp.referencia3) as numero_factura, fp.fecha as fecha_factura, coalesce(fp.fecha_recepcion,fp.fecha) as fecha_recepcion_factura,
	fp.id_factura_proveedor, fp.descripcion as descripcion_factura, lap.descripcion as descripcion_documento, lap.fecha as fecha_documento, concat(lap.numero,'/',ap.numero) as numero_documento,
	df.codigo as codigo_documento, d.nombre as nombre_documento,
	cxp.id_cuenta_por_pagar, cxp.fecha_vencimiento as fecha_vence, 0 as debito, dlap.valor as credito,fp.id_organizacion as id_organizacion,t.id_tipo_operacion,t.nombre as tipo_operacion, 
	d.codigo as codigo_documento_proceso, null as asiento_compra, a.numero as asiento_documento, 0.00 as valor_retenido, fp.referencia1, fp.referencia2, fp.valor_referencia1, fp.valor_referencia2, fp.valor_referencia3,
	catem.id_categoria_empresa as id_categoria_empresa, catem.nombre as nombre_categoria_empresa, d.documento_base documento_base, null as id_pago,  fps.fecha_emision as fecha_emision
	from empresa em
	inner join factura_proveedor fp on fp.id_empresa = em.id_empresa
	inner join categoria_empresa catem on em.id_categoria_empresa = catem.id_categoria_empresa
	inner join documento df on fp.id_documento = df.id_documento
	inner join cuenta_por_pagar cxp on cxp.id_factura_proveedor = fp.id_factura_proveedor
	inner join detalle_liquidacion_anticipo_proveedor dlap on dlap.id_cuenta_por_pagar = cxp.id_cuenta_por_pagar
	inner join liquidacion_anticipo_proveedor lap on lap.id_liquidacion_anticipo_proveedor = dlap.id_liquidacion_anticipo_proveedor
	inner join anticipo_proveedor ap on ap.id_anticipo_proveedor = lap.id_anticipo_proveedor
	inner join documento d on d.id_documento = lap.id_documento
	left outer join factura_proveedorSRI fps on fps.id_factura_proveedor = fp.id_factura_proveedor
	left outer join tipo_operacion t on t.id_tipo_operacion = fp.id_tipo_operacion
	left outer join asiento a on a.id_asiento = lap.id_asiento
	left outer join sucursal s on s.id_sucursal = fp.id_sucursal
	where case when d.indicador_contabilizar = false and lap.estado <> 0 then 4 else lap.estado end >=4
	);