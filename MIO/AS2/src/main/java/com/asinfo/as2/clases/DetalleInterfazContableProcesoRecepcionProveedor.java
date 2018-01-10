/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DimensionContable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="tmp_detalle_interfaz_contable_proceso_recepcion_proveedor")
/*  15:    */ public class DetalleInterfazContableProcesoRecepcionProveedor
/*  16:    */   extends DetalleInterfazContableProceso
/*  17:    */ {
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/*  21:    */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/*  22:    */   private Integer idDetalleInterfazContableProceso;
/*  23:    */   
/*  24:    */   public DetalleInterfazContableProcesoRecepcionProveedor() {}
/*  25:    */   
/*  26:    */   public DetalleInterfazContableProcesoRecepcionProveedor(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer proveedor, String nombreProveedor, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer bodega, String nombreBodega, String descripcion, BigDecimal valor)
/*  27:    */   {
/*  28: 59 */     setDocumento(documento);
/*  29: 60 */     setNombreDocumento(nombreDocumento);
/*  30: 61 */     setSucursal(sucursal);
/*  31: 62 */     setNombreSucursal(nombreSucursal);
/*  32: 63 */     setCategoriaEmpresa(categoriaEmpresa);
/*  33: 64 */     setNombreCategoriaEmpresa(nombreCategoriaEmpresa);
/*  34: 65 */     setProveedor(proveedor);
/*  35: 66 */     setNombreProveedor(nombreProveedor);
/*  36: 67 */     setCategoriaProducto(categoriaProducto);
/*  37: 68 */     setNombreCategoriaProducto(nombreCategoriaProducto);
/*  38: 69 */     setSubcategoriaProducto(subcategoriaProducto);
/*  39: 70 */     setNombreSubcategoriaProducto(nombreSubcategoriaProducto);
/*  40: 71 */     setProducto(producto);
/*  41: 72 */     setNombreProducto(nombreProducto);
/*  42: 73 */     setBodega(bodega);
/*  43: 74 */     setNombreBodega(nombreBodega);
/*  44: 75 */     setDescripcion(descripcion);
/*  45: 76 */     setValor(valor);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public DetalleInterfazContableProcesoRecepcionProveedor(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer proveedor, String nombreProveedor, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer bodega, String nombreBodega, String descripcion, BigDecimal valor, Integer idDimensionContable1, Integer idDimensionContable2, Integer idDimensionContable3, Integer idDimensionContable4, Integer idDimensionContable5)
/*  49:    */   {
/*  50: 96 */     setDocumento(documento);
/*  51: 97 */     setNombreDocumento(nombreDocumento);
/*  52: 98 */     setSucursal(sucursal);
/*  53: 99 */     setNombreSucursal(nombreSucursal);
/*  54:100 */     setCategoriaEmpresa(categoriaEmpresa);
/*  55:101 */     setNombreCategoriaEmpresa(nombreCategoriaEmpresa);
/*  56:102 */     setProveedor(proveedor);
/*  57:103 */     setNombreProveedor(nombreProveedor);
/*  58:104 */     setCategoriaProducto(categoriaProducto);
/*  59:105 */     setNombreCategoriaProducto(nombreCategoriaProducto);
/*  60:106 */     setSubcategoriaProducto(subcategoriaProducto);
/*  61:107 */     setNombreSubcategoriaProducto(nombreSubcategoriaProducto);
/*  62:108 */     setProducto(producto);
/*  63:109 */     setNombreProducto(nombreProducto);
/*  64:110 */     setBodega(bodega);
/*  65:111 */     setNombreBodega(nombreBodega);
/*  66:112 */     setDescripcion(descripcion);
/*  67:113 */     setValor(valor);
/*  68:115 */     if (idDimensionContable1 != null)
/*  69:    */     {
/*  70:116 */       DimensionContable dimensionContable = new DimensionContable();
/*  71:117 */       dimensionContable.setIdDimensionContable(idDimensionContable1.intValue());
/*  72:118 */       setDimensionContable1(dimensionContable);
/*  73:    */     }
/*  74:120 */     if (idDimensionContable2 != null)
/*  75:    */     {
/*  76:121 */       DimensionContable dimensionContable = new DimensionContable();
/*  77:122 */       dimensionContable.setIdDimensionContable(idDimensionContable2.intValue());
/*  78:123 */       setDimensionContable2(dimensionContable);
/*  79:    */     }
/*  80:125 */     if (idDimensionContable3 != null)
/*  81:    */     {
/*  82:126 */       DimensionContable dimensionContable = new DimensionContable();
/*  83:127 */       dimensionContable.setIdDimensionContable(idDimensionContable3.intValue());
/*  84:128 */       setDimensionContable3(dimensionContable);
/*  85:    */     }
/*  86:130 */     if (idDimensionContable4 != null)
/*  87:    */     {
/*  88:131 */       DimensionContable dimensionContable = new DimensionContable();
/*  89:132 */       dimensionContable.setIdDimensionContable(idDimensionContable4.intValue());
/*  90:133 */       setDimensionContable4(dimensionContable);
/*  91:    */     }
/*  92:135 */     if (idDimensionContable5 != null)
/*  93:    */     {
/*  94:136 */       DimensionContable dimensionContable = new DimensionContable();
/*  95:137 */       dimensionContable.setIdDimensionContable(idDimensionContable5.intValue());
/*  96:138 */       setDimensionContable5(dimensionContable);
/*  97:    */     }
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoRecepcionProveedor
 * JD-Core Version:    0.7.0.1
 */