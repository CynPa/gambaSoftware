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
/*  13:    */ @Table(name="tmp_detalle_interfaz_contable_proceso_despachos")
/*  14:    */ public class DetalleInterfazContableProcesoDespachos
/*  15:    */   extends DetalleInterfazContableProceso
/*  16:    */ {
/*  17:    */   @Id
/*  18:    */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/*  19:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/*  20:    */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/*  21:    */   private Integer idDetalleInterfazContableProceso;
/*  22:    */   
/*  23:    */   public DetalleInterfazContableProcesoDespachos() {}
/*  24:    */   
/*  25:    */   public DetalleInterfazContableProcesoDespachos(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer cliente, String nombreCliente, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer bodega, String nombreBodega, Integer subcliente, String nombreSubcliente, String descripcion, BigDecimal valor, String agrupamiento)
/*  26:    */   {
/*  27: 72 */     setDocumento(documento);
/*  28: 73 */     setNombreDocumento(nombreDocumento);
/*  29: 74 */     setSucursal(sucursal);
/*  30: 75 */     setNombreSucursal(nombreSucursal);
/*  31: 76 */     setCategoriaEmpresa(categoriaEmpresa);
/*  32: 77 */     setNombreCategoriaEmpresa(nombreCategoriaEmpresa);
/*  33: 78 */     setCliente(cliente);
/*  34: 79 */     setNombreCliente(nombreCliente);
/*  35: 80 */     setCategoriaProducto(categoriaProducto);
/*  36: 81 */     setNombreCategoriaProducto(nombreCategoriaProducto);
/*  37: 82 */     setSubcategoriaProducto(subcategoriaProducto);
/*  38: 83 */     setNombreSubcategoriaProducto(nombreSubcategoriaProducto);
/*  39: 84 */     setProducto(producto);
/*  40: 85 */     setNombreProducto(nombreProducto);
/*  41: 86 */     setBodega(bodega);
/*  42: 87 */     setNombreBodega(nombreBodega);
/*  43: 88 */     setSubcliente(subcliente);
/*  44: 89 */     setNombreCliente(nombreCliente);
/*  45: 90 */     setDescripcion(descripcion);
/*  46: 91 */     setValor(valor);
/*  47: 92 */     setAgrupamiento(agrupamiento);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public DetalleInterfazContableProcesoDespachos(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer cliente, String nombreCliente, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer bodega, String nombreBodega, Integer subcliente, String nombreSubcliente, Integer motivoNotaCredito, String nombreMotivoNotaCredito, String descripcion, BigDecimal valor)
/*  51:    */   {
/*  52:100 */     setDocumento(documento);
/*  53:101 */     setNombreDocumento(nombreDocumento);
/*  54:102 */     setSucursal(sucursal);
/*  55:103 */     setNombreSucursal(nombreSucursal);
/*  56:104 */     setCategoriaEmpresa(categoriaEmpresa);
/*  57:105 */     setNombreCategoriaEmpresa(nombreCategoriaEmpresa);
/*  58:106 */     setCliente(cliente);
/*  59:107 */     setNombreCliente(nombreCliente);
/*  60:108 */     setCategoriaProducto(categoriaProducto);
/*  61:109 */     setNombreCategoriaProducto(nombreCategoriaProducto);
/*  62:110 */     setSubcategoriaProducto(subcategoriaProducto);
/*  63:111 */     setNombreSubcategoriaProducto(nombreSubcategoriaProducto);
/*  64:112 */     setProducto(producto);
/*  65:113 */     setNombreProducto(nombreProducto);
/*  66:114 */     setBodega(bodega);
/*  67:115 */     setNombreBodega(nombreBodega);
/*  68:116 */     setSubcliente(subcliente);
/*  69:117 */     setNombreCliente(nombreCliente);
/*  70:118 */     setDescripcion(descripcion);
/*  71:119 */     setValor(valor);
/*  72:    */   }
/*  73:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoDespachos
 * JD-Core Version:    0.7.0.1
 */