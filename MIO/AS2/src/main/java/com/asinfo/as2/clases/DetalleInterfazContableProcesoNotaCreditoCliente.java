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
/*  14:    */ @Table(name="tmp_detalle_interfaz_contable_proceso_nota_credito_cliente")
/*  15:    */ public class DetalleInterfazContableProcesoNotaCreditoCliente
/*  16:    */   extends DetalleInterfazContableProceso
/*  17:    */ {
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/*  21:    */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/*  22:    */   private Integer idDetalleInterfazContableProceso;
/*  23:    */   
/*  24:    */   public DetalleInterfazContableProcesoNotaCreditoCliente() {}
/*  25:    */   
/*  26:    */   public DetalleInterfazContableProcesoNotaCreditoCliente(Integer documento, Integer sucursal, Integer categoriaEmpresa, Integer cliente, Integer categoriaProducto, Integer subcategoriaProducto, Integer producto, Integer motivoNotaCreditoCliente, Integer subcliente, Integer impuesto, String descripcion, BigDecimal valor)
/*  27:    */   {
/*  28: 55 */     setDocumento(documento);
/*  29: 56 */     setSucursal(sucursal);
/*  30: 57 */     setCategoriaEmpresa(categoriaEmpresa);
/*  31: 58 */     setCliente(cliente);
/*  32: 59 */     setCategoriaProducto(categoriaProducto);
/*  33: 60 */     setSubcategoriaProducto(subcategoriaProducto);
/*  34: 61 */     setProducto(producto);
/*  35: 62 */     setMotivoNotaCreditoCliente(motivoNotaCreditoCliente);
/*  36: 63 */     setSubcliente(subcliente);
/*  37: 64 */     setImpuesto(impuesto);
/*  38: 65 */     setDescripcion(descripcion);
/*  39: 66 */     setValor(valor);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public DetalleInterfazContableProcesoNotaCreditoCliente(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer cliente, String nombreCliente, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer motivoNotaCreditoCliente, String nombreMotivoNotaCreditoCliente, Integer subcliente, String nombreSubcliente, Integer impuesto, String nombreImpuesto, Integer idProyecto, String nombreProyecto, Integer categoriaImpuesto, String nombreCategoriaImpuesto, String descripcion, BigDecimal valor)
/*  43:    */   {
/*  44: 82 */     setDocumento(documento);
/*  45: 83 */     setDocumento(documento);
/*  46:    */     
/*  47: 85 */     setSucursal(sucursal);
/*  48: 86 */     setNombreSucursal(nombreSucursal);
/*  49:    */     
/*  50: 88 */     setCategoriaEmpresa(categoriaEmpresa);
/*  51: 89 */     setNombreCategoriaEmpresa(nombreCategoriaEmpresa);
/*  52:    */     
/*  53: 91 */     setCliente(cliente);
/*  54: 92 */     setNombreCliente(nombreCliente);
/*  55:    */     
/*  56: 94 */     setCategoriaProducto(categoriaProducto);
/*  57: 95 */     setNombreCategoriaProducto(nombreCategoriaProducto);
/*  58:    */     
/*  59: 97 */     setSubcategoriaProducto(subcategoriaProducto);
/*  60: 98 */     setNombreSubcategoriaProducto(nombreSubcategoriaProducto);
/*  61:    */     
/*  62:100 */     setProducto(producto);
/*  63:101 */     setNombreProducto(nombreProducto);
/*  64:    */     
/*  65:103 */     setMotivoNotaCreditoCliente(motivoNotaCreditoCliente);
/*  66:104 */     setNombreMotivoNotaCreditoCliente(nombreMotivoNotaCreditoCliente);
/*  67:    */     
/*  68:106 */     setSubcliente(subcliente);
/*  69:107 */     setNombreSubcliente(nombreSubcliente);
/*  70:    */     
/*  71:109 */     setImpuesto(impuesto);
/*  72:110 */     setNombreImpuesto(nombreImpuesto);
/*  73:    */     
/*  74:112 */     setDescripcion(descripcion);
/*  75:113 */     setValor(valor);
/*  76:    */     
/*  77:115 */     setCategoriaImpuesto(categoriaImpuesto);
/*  78:116 */     setNombreCategoriaImpuesto(nombreCategoriaImpuesto);
/*  79:118 */     if ((idProyecto != null) && (!idProyecto.equals(Integer.valueOf(0))))
/*  80:    */     {
/*  81:119 */       DimensionContable proyecto = new DimensionContable();
/*  82:120 */       proyecto.setIdDimensionContable(idProyecto.intValue());
/*  83:121 */       proyecto.setNombre(nombreProyecto);
/*  84:122 */       setDimensionContable5(proyecto);
/*  85:    */     }
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoNotaCreditoCliente
 * JD-Core Version:    0.7.0.1
 */