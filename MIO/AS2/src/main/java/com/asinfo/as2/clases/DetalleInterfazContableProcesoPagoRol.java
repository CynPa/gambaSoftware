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
/* 13:   */ @Table(name="tmp_detalle_interfaz_contable_proceso_pago_rol")
/* 14:   */ public class DetalleInterfazContableProcesoPagoRol
/* 15:   */   extends DetalleInterfazContableProceso
/* 16:   */ {
/* 17:   */   @Id
/* 18:   */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/* 19:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/* 20:   */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/* 21:   */   private Integer idDetalleInterfazContableProceso;
/* 22:   */   
/* 23:   */   public DetalleInterfazContableProcesoPagoRol() {}
/* 24:   */   
/* 25:   */   public DetalleInterfazContableProcesoPagoRol(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer empleado, String nombreEmpleado, Integer rubro, String nombreRubro, Integer departamento, String nombreDepartamento, String descripcion, String agrupamiento, BigDecimal valor)
/* 26:   */   {
/* 27:47 */     setDocumento(documento);
/* 28:48 */     setNombreDocumento(nombreDocumento);
/* 29:   */     
/* 30:50 */     setSucursal(sucursal);
/* 31:51 */     setNombreSucursal(nombreSucursal);
/* 32:   */     
/* 33:53 */     setCategoriaEmpresa(categoriaEmpresa);
/* 34:54 */     setNombreCategoriaEmpresa(nombreCategoriaEmpresa);
/* 35:   */     
/* 36:56 */     setEmpleado(empleado);
/* 37:57 */     setNombreEmpleado(nombreEmpleado);
/* 38:   */     
/* 39:59 */     setRubro(rubro);
/* 40:60 */     setNombreRubro(nombreRubro);
/* 41:   */     
/* 42:62 */     setDepartamento(departamento);
/* 43:63 */     setNombreDepartamento(nombreDepartamento);
/* 44:64 */     setDescripcion(descripcion);
/* 45:65 */     setAgrupamiento(agrupamiento);
/* 46:66 */     setValor(valor);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public DetalleInterfazContableProcesoPagoRol(Integer documento, String nombreDocumento, Integer sucursal, String nombreSucursal, Integer categoriaEmpresa, String nombreCategoriaEmpresa, Integer empleado, String nombreEmpleado, Integer departamento, String nombreDepartamento, String descripcion, String agrupamiento, BigDecimal valor)
/* 50:   */   {
/* 51:73 */     this(documento, nombreDocumento, sucursal, nombreSucursal, categoriaEmpresa, nombreCategoriaEmpresa, empleado, nombreEmpleado, null, null, departamento, nombreDepartamento, agrupamiento, descripcion, valor);
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoPagoRol
 * JD-Core Version:    0.7.0.1
 */