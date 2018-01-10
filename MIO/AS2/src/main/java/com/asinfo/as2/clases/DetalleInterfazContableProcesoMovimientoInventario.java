/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="tmp_detalle_interfaz_contable_proceso_movimiento_inventario")
/*  14:    */ public class DetalleInterfazContableProcesoMovimientoInventario
/*  15:    */   extends DetalleInterfazContableProceso
/*  16:    */ {
/*  17:    */   @Id
/*  18:    */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/*  19:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/*  20:    */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/*  21:    */   private Integer idDetalleInterfazContableProceso;
/*  22:    */   
/*  23:    */   public DetalleInterfazContableProcesoMovimientoInventario() {}
/*  24:    */   
/*  25:    */   public DetalleInterfazContableProcesoMovimientoInventario(Integer documento, Integer sucursal, Integer categoriaProducto, Integer subcategoriaProducto, Integer producto, Integer bodega, Integer motivoAjusteInventario, String descripcion, BigDecimal valor)
/*  26:    */   {
/*  27: 70 */     setDocumento(documento);
/*  28: 71 */     setSucursal(sucursal);
/*  29: 72 */     setCategoriaProducto(categoriaProducto);
/*  30: 73 */     setSubcategoriaProducto(subcategoriaProducto);
/*  31: 74 */     setProducto(producto);
/*  32: 75 */     setBodega(bodega);
/*  33: 76 */     setMotivoAjusteInventario(motivoAjusteInventario);
/*  34: 77 */     setDescripcion(descripcion);
/*  35: 78 */     setValor(valor);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public DetalleInterfazContableProcesoMovimientoInventario(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer bodega, String nombreBodega, Integer motivoAjusteInventario, String nombreMotivoAjusteInventario, String descripcion, BigDecimal valor)
/*  39:    */   {
/*  40: 97 */     setDocumento(documento);
/*  41: 98 */     setNombreDocumento(nombreDocumento);
/*  42: 99 */     setSucursal(sucursal);
/*  43:100 */     setNombreSucursal(nombreSucursal);
/*  44:101 */     setCategoriaProducto(categoriaProducto);
/*  45:102 */     setNombreCategoriaProducto(nombreCategoriaProducto);
/*  46:103 */     setSubcategoriaProducto(subcategoriaProducto);
/*  47:104 */     setNombreSubcategoriaProducto(nombreSubcategoriaProducto);
/*  48:105 */     setProducto(producto);
/*  49:106 */     setNombreProducto(nombreProducto);
/*  50:107 */     setBodega(bodega);
/*  51:108 */     setNombreBodega(nombreBodega);
/*  52:109 */     setMotivoAjusteInventario(motivoAjusteInventario);
/*  53:110 */     setNombreMotivoAjusteInventario(nombreMotivoAjusteInventario);
/*  54:111 */     setDescripcion(descripcion);
/*  55:112 */     setValor(valor);
/*  56:    */   }
/*  57:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoMovimientoInventario
 * JD-Core Version:    0.7.0.1
 */