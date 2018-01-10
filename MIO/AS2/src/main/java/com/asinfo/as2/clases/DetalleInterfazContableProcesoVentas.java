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
/*  14:    */ @Table(name="tmp_detalle_interfaz_contable_proceso_ventas")
/*  15:    */ public class DetalleInterfazContableProcesoVentas
/*  16:    */   extends DetalleInterfazContableProceso
/*  17:    */ {
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/*  21:    */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/*  22:    */   private Integer idDetalleInterfazContableProceso;
/*  23:    */   
/*  24:    */   public DetalleInterfazContableProcesoVentas() {}
/*  25:    */   
/*  26:    */   public DetalleInterfazContableProcesoVentas(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer cliente, String nombreCliente, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer canal, String nombreCanal, Integer subcliente, String nombreSubcliente, Integer zona, String nombreZona, Integer impuesto, String nombreImpuesto, Integer idProyecto, String nombreProyecto, Integer categoriaImpuesto, String nombreCategoriaImpuesto, String descripcion, String agrupamiento, BigDecimal valor)
/*  27:    */   {
/*  28: 76 */     setDocumento(documento);
/*  29: 77 */     setNombreDocumento(nombreDocumento);
/*  30: 78 */     setSucursal(sucursal);
/*  31: 79 */     setNombreSucursal(nombreSucursal);
/*  32: 80 */     setCategoriaEmpresa(categoriaEmpresa);
/*  33: 81 */     setNombreCategoriaEmpresa(nombreCategoriaEmpresa);
/*  34: 82 */     setCliente(cliente);
/*  35: 83 */     setNombreCliente(nombreCliente);
/*  36: 84 */     setCategoriaProducto(categoriaProducto);
/*  37: 85 */     setNombreCategoriaProducto(nombreCategoriaProducto);
/*  38: 86 */     setSubcategoriaProducto(subcategoriaProducto);
/*  39: 87 */     setNombreSubcategoriaProducto(nombreSubcategoriaProducto);
/*  40: 88 */     setProducto(producto);
/*  41: 89 */     setNombreProducto(nombreProducto);
/*  42: 90 */     setCanal(canal);
/*  43: 91 */     setNombreCanal(nombreCanal);
/*  44: 92 */     setSubcliente(subcliente);
/*  45: 93 */     setNombreSubcliente(nombreSubcliente);
/*  46: 94 */     setZona(zona);
/*  47: 95 */     setNombreZona(nombreZona);
/*  48: 96 */     setImpuesto(impuesto);
/*  49: 97 */     setNombreImpuesto(nombreImpuesto);
/*  50: 98 */     setCategoriaImpuesto(categoriaImpuesto);
/*  51: 99 */     setNombreCategoriaImpuesto(nombreCategoriaImpuesto);
/*  52:100 */     setNombreImpuesto(nombreImpuesto);
/*  53:101 */     setDescripcion(descripcion);
/*  54:102 */     setAgrupamiento(agrupamiento);
/*  55:103 */     setValor(valor);
/*  56:105 */     if ((idProyecto != null) && (!idProyecto.equals(Integer.valueOf(0))))
/*  57:    */     {
/*  58:106 */       DimensionContable proyecto = new DimensionContable();
/*  59:107 */       proyecto.setIdDimensionContable(idProyecto.intValue());
/*  60:108 */       proyecto.setNombre(nombreProyecto);
/*  61:109 */       setDimensionContable5(proyecto);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public DetalleInterfazContableProcesoVentas(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer cliente, String nombreCliente, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer canal, String nombreCanal, Integer subcliente, String nombreSubcliente, Integer zona, String nombreZona, Integer impuesto, String nombreImpuesto, Integer idProyecto, String nombreProyecto, Integer categoriaImpuesto, String nombreCategoriaImpuesto, String descripcion, String agrupamiento, BigDecimal valor, BigDecimal descuentoImpuesto, Integer idFactura)
/*  66:    */   {
/*  67:119 */     this(documento, nombreDocumento, sucursal, nombreSucursal, categoriaEmpresa, nombreCategoriaEmpresa, cliente, nombreCliente, categoriaProducto, nombreCategoriaProducto, subcategoriaProducto, nombreSubcategoriaProducto, producto, nombreProducto, canal, nombreCanal, subcliente, nombreSubcliente, zona, nombreZona, impuesto, nombreImpuesto, idProyecto, nombreProyecto, categoriaImpuesto, nombreCategoriaImpuesto, descripcion, agrupamiento, valor);
/*  68:    */     
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:125 */     setDescuentoImpuesto(descuentoImpuesto);
/*  74:126 */     setIdFacturaCliente(idFactura);
/*  75:    */   }
/*  76:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoVentas
 * JD-Core Version:    0.7.0.1
 */