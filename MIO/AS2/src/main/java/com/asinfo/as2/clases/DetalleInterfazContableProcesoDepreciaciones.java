/*  1:   */ package com.asinfo.as2.clases;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.DimensionContable;
/*  4:   */ import java.math.BigDecimal;
/*  5:   */ import javax.persistence.Column;
/*  6:   */ import javax.persistence.Entity;
/*  7:   */ import javax.persistence.GeneratedValue;
/*  8:   */ import javax.persistence.GenerationType;
/*  9:   */ import javax.persistence.Id;
/* 10:   */ import javax.persistence.Table;
/* 11:   */ import javax.persistence.TableGenerator;
/* 12:   */ 
/* 13:   */ @Entity
/* 14:   */ @Table(name="tmp_detalle_interfaz_contable_proceso_depreciaciones")
/* 15:   */ public class DetalleInterfazContableProcesoDepreciaciones
/* 16:   */   extends DetalleInterfazContableProceso
/* 17:   */ {
/* 18:   */   @Id
/* 19:   */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/* 20:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/* 21:   */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/* 22:   */   private Integer idDetalleInterfazContableProceso;
/* 23:   */   
/* 24:   */   public DetalleInterfazContableProcesoDepreciaciones() {}
/* 25:   */   
/* 26:   */   public DetalleInterfazContableProcesoDepreciaciones(Integer sucursal, String nombreSucursal, Integer departamento, String nombreDepartamento, Integer categoriaActivo, String nombreCategoriaActivo, Integer subcategoriaActivo, String nombreSubcategoriaActivo, Integer activoFijo, String nombreActivoFijo, String descripcion, String agrupamiento, BigDecimal valor)
/* 27:   */   {
/* 28:62 */     setSucursal(sucursal);
/* 29:63 */     setNombreSucursal(nombreSucursal);
/* 30:64 */     setDepartamento(departamento);
/* 31:65 */     setNombreDepartamento(nombreDepartamento);
/* 32:66 */     setCategoriaActivo(categoriaActivo);
/* 33:67 */     setNombreCategoriaActivo(nombreCategoriaActivo);
/* 34:68 */     setSubcategoriaActivo(subcategoriaActivo);
/* 35:69 */     setNombreSubcategoriaActivo(nombreSubcategoriaActivo);
/* 36:70 */     setActivoFijo(activoFijo);
/* 37:71 */     setNombreActivoFijo(nombreActivoFijo);
/* 38:72 */     setAgrupamiento(agrupamiento);
/* 39:73 */     setDescripcion(descripcion);
/* 40:74 */     setValor(valor);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public DetalleInterfazContableProcesoDepreciaciones(Integer sucursal, String nombreSucursal, Integer departamento, String nombreDepartamento, Integer categoriaActivo, String nombreCategoriaActivo, Integer subcategoriaActivo, String nombreSubcategoriaActivo, Integer activoFijo, String nombreActivoFijo, String descripcion, String agrupamiento, BigDecimal valor, Integer idCentroCosto, String nombreCentroCosto)
/* 44:   */   {
/* 45:80 */     this(sucursal, nombreSucursal, departamento, nombreDepartamento, categoriaActivo, nombreCategoriaActivo, subcategoriaActivo, nombreSubcategoriaActivo, activoFijo, nombreActivoFijo, descripcion, agrupamiento, valor);
/* 46:   */     
/* 47:82 */     DimensionContable centroCosto = new DimensionContable();
/* 48:83 */     if (idCentroCosto != null)
/* 49:   */     {
/* 50:84 */       centroCosto.setIdDimensionContable(idCentroCosto.intValue());
/* 51:85 */       centroCosto.setNombre(nombreCentroCosto);
/* 52:86 */       setDimensionContable1(centroCosto);
/* 53:   */     }
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoDepreciaciones
 * JD-Core Version:    0.7.0.1
 */