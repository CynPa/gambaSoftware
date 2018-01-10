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
/*  13:    */ @Table(name="tmp_detalle_interfaz_contable_proceso_prefactura_cliente")
/*  14:    */ public class DetalleInterfazContableProcesoPrefacturaCliente
/*  15:    */   extends DetalleInterfazContableProceso
/*  16:    */ {
/*  17:    */   @Id
/*  18:    */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/*  19:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/*  20:    */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/*  21:    */   private Integer idDetalleInterfazContableProceso;
/*  22:    */   private String clave;
/*  23:    */   
/*  24:    */   public DetalleInterfazContableProcesoPrefacturaCliente() {}
/*  25:    */   
/*  26:    */   public DetalleInterfazContableProcesoPrefacturaCliente(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer cliente, String nombreCliente, Integer categoriaProducto, String nombreCategoriaProducto, Integer subcategoriaProducto, String nombreSubcategoriaProducto, Integer producto, String nombreProducto, Integer canal, String nombreCanal, Integer subcliente, String nombreSubcliente, Integer zona, String nombreZona, String descripcion, BigDecimal valor)
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
/*  48: 96 */     setDescripcion(descripcion);
/*  49: 97 */     setValor(valor);
/*  50:    */     
/*  51: 99 */     this.clave = (documento != null ? documento.toString() : "");
/*  52:100 */     this.clave += (sucursal != null ? sucursal.toString() : "");
/*  53:101 */     this.clave += (categoriaEmpresa != null ? categoriaEmpresa.toString() : "");
/*  54:102 */     this.clave += (cliente != null ? cliente.toString() : "");
/*  55:103 */     this.clave += (categoriaProducto != null ? categoriaProducto.toString() : "");
/*  56:104 */     this.clave += (subcategoriaProducto != null ? subcategoriaProducto.toString() : "");
/*  57:105 */     this.clave += (producto != null ? producto.toString() : "");
/*  58:106 */     this.clave += (canal != null ? canal.toString() : "");
/*  59:107 */     this.clave += (subcliente != null ? subcliente.toString() : "");
/*  60:108 */     this.clave += (zona != null ? zona.toString() : "");
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getClave()
/*  64:    */   {
/*  65:117 */     return this.clave;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setClave(String clave)
/*  69:    */   {
/*  70:127 */     this.clave = clave;
/*  71:    */   }
/*  72:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoPrefacturaCliente
 * JD-Core Version:    0.7.0.1
 */