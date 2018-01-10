/*  1:   */ package com.asinfo.as2.clases;
/*  2:   */ 
/*  3:   */ import java.math.BigDecimal;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import javax.persistence.Entity;
/*  6:   */ import javax.persistence.GeneratedValue;
/*  7:   */ import javax.persistence.GenerationType;
/*  8:   */ import javax.persistence.Id;
/*  9:   */ import javax.persistence.Table;
/* 10:   */ import javax.persistence.TableGenerator;
/* 11:   */ 
/* 12:   */ @Entity
/* 13:   */ @Table(name="tmp_detalle_interfaz_contable_proceso_factura_proveedor")
/* 14:   */ public class DetalleInterfazContableProcesoFacturaProveedor
/* 15:   */   extends DetalleInterfazContableProceso
/* 16:   */ {
/* 17:   */   @Id
/* 18:   */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/* 19:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/* 20:   */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/* 21:   */   private Integer idDetalleInterfazContableProceso;
/* 22:   */   
/* 23:   */   public DetalleInterfazContableProcesoFacturaProveedor() {}
/* 24:   */   
/* 25:   */   public DetalleInterfazContableProcesoFacturaProveedor(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer proveedor, String nombreProveedor, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer impuesto, String nombreImpuesto, String descripcion, String agrupamiento, BigDecimal valor, BigDecimal impuestosComercializadora)
/* 26:   */   {
/* 27:47 */     this(documento, nombreDocumento, sucursal, nombreSucursal, categoriaEmpresa, nombreCategoriaEmpresa, proveedor, nombreProveedor, categoriaProducto, nombreCategoriaProducto, subcategoriaProducto, nombreSubcategoriaProducto, producto, nombreProducto, impuesto, nombreImpuesto, descripcion, agrupamiento, valor);
/* 28:   */     
/* 29:   */ 
/* 30:   */ 
/* 31:51 */     setImpuestosComercializadora(impuestosComercializadora);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public DetalleInterfazContableProcesoFacturaProveedor(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer proveedor, String nombreProveedor, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer impuesto, String nombreImpuesto, String descripcion, String agrupamiento, BigDecimal valor)
/* 35:   */   {
/* 36:70 */     setDocumento(documento);
/* 37:71 */     setNombreDocumento(nombreDocumento);
/* 38:72 */     setSucursal(sucursal);
/* 39:73 */     setNombreSucursal(nombreSucursal);
/* 40:74 */     setCategoriaEmpresa(categoriaEmpresa);
/* 41:75 */     setNombreCategoriaEmpresa(nombreCategoriaEmpresa);
/* 42:76 */     setProveedor(proveedor);
/* 43:77 */     setNombreProveedor(nombreProveedor);
/* 44:78 */     setCategoriaProducto(categoriaProducto);
/* 45:79 */     setNombreCategoriaProducto(nombreCategoriaProducto);
/* 46:80 */     setSubcategoriaProducto(subcategoriaProducto);
/* 47:81 */     setNombreSubcategoriaProducto(nombreSubcategoriaProducto);
/* 48:82 */     setProducto(producto);
/* 49:83 */     setNombreProducto(nombreProducto);
/* 50:84 */     setImpuesto(impuesto);
/* 51:85 */     setNombreImpuesto(nombreImpuesto);
/* 52:86 */     setDescripcion(descripcion);
/* 53:87 */     setAgrupamiento(agrupamiento);
/* 54:88 */     setValor(valor);
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoFacturaProveedor
 * JD-Core Version:    0.7.0.1
 */