package com.gmb.as2.eao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.Session;

import com.gmb.conexion.GmbConexion;
import com.gmb.modelo.GmbNivelOperacion;

@Stateless
public class GenericReport extends GmbConexion<GmbNivelOperacion, Serializable> {

	@SuppressWarnings("unchecked") 
	public List<Object[]> getAs2Reporte1() {
		Query query = entityMgr2.createNativeQuery("select distinct(p.codigo),p.nombre,dmi.cantidad,"
				+ "((select sum(cantidad) from inventario_producto where id_producto=ip.id_producto and operacion=1)-  "
				+ "(select sum(cantidad) from inventario_producto where id_producto=ip.id_producto and operacion=-1))saldo,  "
				+ "(select cantidad from inventario_producto where id_producto=ip.id_producto and operacion=1 order by fecha_creacion desc  limit 1) "
				+ " cantidad_ultima_ingreso,ip.fecha ,sp.nombre subcategoria,cp.nombre categoria from producto p,detalle_movimiento_inventario dmi, "
				+ " bodega b, inventario_producto ip,subcategoria_producto sp,categoria_producto cp where p.id_producto=dmi.id_producto "
				+ "and dmi.id_detalle_movimiento_inventario=ip.id_detalle_movimiento_inventario and dmi.fecha_creacion=(select max(fecha_creacion) "
				+ "from detalle_movimiento_inventario where id_producto=p.id_producto) and dmi.id_bodega_origen=b.id_bodega "
				+ "and p.id_subcategoria_producto=sp.id_subcategoria_producto and sp.id_categoria_producto=cp.id_categoria_producto");
		List<Object[]> lista = query.getResultList();
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getAs2Reporte2() {
		Query query = entityMgr2.createNativeQuery(""
				+ "select  /*pro.id_proveedor,*/prod.codigo,prod.nombre_comercial , emp.nombre_comercial empresa_comercial "
				+ ",emp.nombre_fiscal nombre_empresa_fiscal, factpro.descripcion descripcion_factura,ctspagar.numero_cuota, "
				+ "detfacpro.cantidad,detfacpro.precio, factpro.impuesto,ctspagar.valor,ctspagar.saldo, cond_pago.nombre condicion_pago  "
				+ ",sp.nombre subcategoria,cp.nombre categoria from proveedor pro, empresa emp,factura_proveedor factpro, cuenta_por_pagar "
				+ " ctspagar,detalle_factura_proveedor detfacpro, producto prod,condicion_pago cond_pago, subcategoria_producto sp, "
				+ "categoria_producto cp where pro.id_empresa =emp.id_empresa and pro.id_empresa=factpro.id_empresa and "
				+ "factpro.id_factura_proveedor=detfacpro.id_factura_proveedor and detfacpro.id_producto=prod.id_producto and "
				+ "factpro.id_factura_proveedor=ctspagar.id_factura_proveedor  "
				+ "and factpro.id_condicion_pago=cond_pago.id_condicion_pago "
				+ "and prod.id_subcategoria_producto=sp.id_subcategoria_producto "
				+ "and sp.id_categoria_producto=cp.id_categoria_producto");
		List<Object[]> lista = query.getResultList();
		return lista;
	}

	public List<Object[]> getAs2Reporte3() {
		Query query = entityMgr2.createNativeQuery(""
				+ "select prod.codigo,prod.nombre_comercial,emp.nombre_comercial empresa_comercial,emp.nombre_fiscal\r\n"
				+ "nombre_empresa_fiscal,fp.descripcion descripcion_factura,cpp.numero_cuota,dfp.cantidad, dfp.precio,\r\n"
				+ "fp.impuesto,cpp.valor,cpp.saldo,cond_pago.nombre condicion_pago,sp.nombre subcategoria,cp.nombre \r\n"
				+ "categoria from cliente cli,cuenta_por_cobrar cpp , factura_cliente fp,empresa emp,detalle_factura_cliente dfp,\r\n"
				+ "producto prod,condicion_pago cond_pago, subcategoria_producto sp,categoria_producto cp where cli.id_empresa=\r\n"
				+ "fp.id_empresa and cpp.id_factura_cliente =fp.id_factura_cliente and fp.id_empresa=emp.id_empresa and \r\n"
				+ "fp.id_factura_cliente=dfp.id_factura_cliente and dfp.id_producto=prod.id_producto and fp.id_condicion_pago=\r\n"
				+ "cond_pago.id_condicion_pago and prod.id_subcategoria_producto=sp.id_subcategoria_producto and sp.id_categoria_producto\r\n"
				+ "=cp.id_categoria_producto");
		List<Object[]> lista = query.getResultList();
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getAs2Reporte4() {
		Query query = entityMgr2.createNativeQuery("" + "select --dmi.id_detalle_movimiento_inventario "
				+ "     ip.fecha,ip.hora_creacion,p.codigo, p.nombre producto,b.nombre,cp.nombre categoria, "
				+ "       sp.nombre subcategoria,ip.costo "
				+ "       from producto p ,bodega b ,inventario_producto ip,subcategoria_producto sp,"
				+ "        categoria_producto cp " + "        where b.id_bodega=ip.id_bodega and"
				+ "      ip.id_producto=p.id_producto and "
				+ "        p.id_subcategoria_producto=sp.id_subcategoria_producto "
				+ "       and sp.id_categoria_producto=cp.id_categoria_producto" + "       --and p.codigo='SUPOAR0001'"
				+ "        and ip.operacion=-1" + "        and ip.fecha  BETWEEN '2017-09-01' AND '2017-09-30'"
				+ "        order by codigo asc");
		List<Object[]> lista = query.getResultList();

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getSaldoBancos(Date fecha) {
		Query query = entityMgr2
				.createNativeQuery(" select cc.nombre,cc.codigo,  sum(da.debe-da.haber) from detalle_asiento  da \r\n"
						+ " inner join asiento a on a.id_asiento=da.id_asiento \r\n"
						+ " inner join cuenta_contable cc on cc.id_cuenta_contable=da.id_cuenta_contable\r\n"
						+ " inner join tipo_asiento ta on ta.id_tipo_asiento=a.id_tipo_asiento\r\n"
						+ " and cc.id_cuenta_contable IN (237,238,240,241,242,243,244,245,246,1109) and a.estado<>0  -- and ta.id_tipo_asiento in (2) \r\n"
						+ " and a.fecha  <= ? " + "    group by cc.nombre,cc.codigo order by cc.codigo asc");
		query.setParameter(1, fecha);
		List<Object[]> lista = query.getResultList();
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getSaldoBodega(Date fechaIni,Date fechaFin) {
		Query query = entityMgr2
				.createNativeQuery(" select p.codigo,un.codigo, p.nombre, p.nombre_Comercial,cp.nombre\r\n" + 
						"  categoria,scp.nombre subcategoria , rp.numero numero_recepcion\r\n" + 
						"  ,rp.numero_factura,rp.fecha,sum(drp.cantidad) cantidad,\r\n" + 
						"  round(SUM(ip.costo),2) costo from inventario_producto ip inner join bodega b\r\n" + 
						"  on ip.id_bodega=b.id_bodega inner join detalle_recepcion_proveedor drp on\r\n" + 
						"  ip.id_detalle_recepcion_proveedor=drp.id_detalle_recepcion_proveedor inner\r\n" + 
						"  join recepcion_proveedor rp on\r\n" + 
						"  drp.id_recepcion_proveedor=rp.id_recepcion_proveedor left join producto p on\r\n" + 
						"  ip.id_producto =p.id_producto left join lote l on ip.id_lote =l.id_lote left\r\n" + 
						"  join subcategoria_producto scp on\r\n" + 
						"  p.id_subcategoria_producto=scp.id_subcategoria_producto left join\r\n" + 
						"  categoria_producto cp on scp.id_categoria_producto = cp.id_categoria_producto\r\n" + 
						"  left join unidad un on drp.id_unidad_compra=un.id_unidad left join\r\n" + 
						"  detalle_factura_proveedor dfp on\r\n" + 
						"  drp.id_detalle_factura_proveedor=dfp.id_detalle_factura_proveedor left join\r\n" + 
						"  factura_proveedor fp on dfp.id_factura_proveedor=fp.id_factura_proveedor left\r\n" + 
						"  join detalle_pedido_proveedor dpp on\r\n" + 
						"  drp.id_detalle_pedido_proveedor=dpp.id_detalle_pedido_proveedor left join\r\n" + 
						"  pedido_proveedor pp on dpp.id_pedido_proveedor =pp.id_pedido_proveedor left\r\n" + 
						"  join empresa e on rp.id_empresa=e.id_empresa and rp.fecha between\r\n" + 
						"  ? and ? GROUP BY p.codigo, un.codigo,\r\n" + 
						"  p.nombre,p.nombre_Comercial,cp.nombre ,scp.nombre , b.codigo, b.nombre,rp.numero\r\n" + 
						"  ,rp.numero_factura,rp.fecha ORDER BY rp.numero asc");
		query.setParameter(1, fechaIni);
		query.setParameter(2, fechaFin);
		List<Object[]> lista = query.getResultList();
		return lista;
	}
}

/*
 * 
 * select p.codigo,p.nombre,dmi.cantidad,b.nombre, ((select sum(cantidad) from
 * inventario_producto where id_producto=ip.id_producto and operacion=1)-
 * (select sum(cantidad) from inventario_producto where
 * id_producto=ip.id_producto and operacion=-1))saldo, (select cantidad from
 * inventario_producto where id_producto=ip.id_producto and operacion=1 order by
 * fecha_creacion desc limit 1) cantidad_ultima_ingreso from producto
 * p,detalle_movimiento_inventario dmi, bodega b, inventario_producto
 * ip--,detalle_recepcion_proveedor drp--,recepcion_proveedor rp--,empresa emp
 * where p.id_producto=dmi.id_producto and
 * dmi.id_detalle_movimiento_inventario=ip.id_detalle_movimiento_inventario and
 * dmi.fecha_creacion=(select max(fecha_creacion) from
 * detalle_movimiento_inventario where id_producto=p.id_producto) and
 * dmi.id_bodega_origen=b.id_bodega ´
 */

/*
 * select ip.fecha,ip.hora_creacion,ip.numero_documento, doc.nombre
 * documento,p.codigo, p.nombre producto,b.nombre bodega, cp.nombre
 * categoria,sp.nombre subcategoria, round( ip.costo, 2) valor,dc.nombre destino
 * -- distinct(ip.numero_documento) from producto p ,bodega b
 * ,inventario_producto ip,subcategoria_producto sp, categoria_producto cp,
 * documento doc--movimiento_inventario mi ,detalle_movimiento_inventario
 * dmi,destino_costo dc where b.id_bodega=ip.id_bodega and
 * ip.id_producto=p.id_producto and ip.id_documento=doc.id_documento and
 * ip.id_detalle_movimiento_inventario=dmi.id_detalle_movimiento_inventario and
 * dmi.id_destino_costo=dc.id_destino_costo and --ip.numero_documento=mi.numero
 * and p.id_subcategoria_producto=sp.id_subcategoria_producto and
 * sp.id_categoria_producto=cp.id_categoria_producto --and p.codigo='SUPOAR0001'
 * and ip.operacion=-1 and ip.numero_documento like 'CBO%'--bod and ip.fecha
 * BETWEEN '2017-09-01' AND '2017-09-30' -- and
 * ip.id_detalle_movimiento_inventario is null order by codigo asc
 */

/*
 * select p.codigo,un.codigo, p.nombre, p.nombre_Comercial,cp.nombre
 * categoria,scp.nombre subcategoria , /rp.numero numero_recepcion
 * ,rp.numero_factura,rp.fecha,sum(drp.cantidad) cantidad,
 * round(SUM(ip.costo),2) costo from inventario_producto ip inner join bodega b
 * on ip.id_bodega=b.id_bodega inner join detalle_recepcion_proveedor drp on
 * ip.id_detalle_recepcion_proveedor=drp.id_detalle_recepcion_proveedor inner
 * join recepcion_proveedor rp on
 * drp.id_recepcion_proveedor=rp.id_recepcion_proveedor left join producto p on
 * ip.id_producto =p.id_producto left join lote l on ip.id_lote =l.id_lote left
 * join subcategoria_producto scp on
 * p.id_subcategoria_producto=scp.id_subcategoria_producto left join
 * categoria_producto cp on scp.id_categoria_producto = cp.id_categoria_producto
 * left join unidad un on drp.id_unidad_compra=un.id_unidad left join
 * detalle_factura_proveedor dfp on
 * drp.id_detalle_factura_proveedor=dfp.id_detalle_factura_proveedor left join
 * factura_proveedor fp on dfp.id_factura_proveedor=fp.id_factura_proveedor left
 * join detalle_pedido_proveedor dpp on
 * drp.id_detalle_pedido_proveedor=dpp.id_detalle_pedido_proveedor left join
 * pedido_proveedor pp on dpp.id_pedido_proveedor =pp.id_pedido_proveedor left
 * join empresa e on rp.id_empresa=e.id_empresa and rp.fecha between
 * '2017-09-01' and '2017-09-30' GROUP BY p.codigo, un.codigo,
 * p.nombre,cp.nombre ,scp.nombre , b.codigo, b.nombre,rp.numero
 * ,rp.numero_factura,rp.fecha ORDER BY rp.numero asc
 */
