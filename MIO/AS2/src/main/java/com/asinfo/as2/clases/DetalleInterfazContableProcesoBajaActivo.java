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
/* 15:   */ public class DetalleInterfazContableProcesoBajaActivo
/* 16:   */   extends DetalleInterfazContableProceso
/* 17:   */ {
/* 18:   */   @Id
/* 19:   */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/* 20:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/* 21:   */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/* 22:   */   private Integer idDetalleInterfazContableProceso;
/* 23:   */   
/* 24:   */   public DetalleInterfazContableProcesoBajaActivo() {}
/* 25:   */   
/* 26:   */   public DetalleInterfazContableProcesoBajaActivo(Integer sucursal, String nombreSucursal, Integer departamento, String nombreDepartamento, Integer categoriaActivo, String nombreCategoriaActivo, Integer subcategoriaActivo, String nombreSubcategoriaActivo, Integer activoFijo, String nombreActivoFijo, Integer motivoBajaActivo, String nombreMotivoBajaActivo, String descripcion, String agrupamiento, BigDecimal valor, Integer idCentroCosto, String nombreCentroCosto)
/* 27:   */   {
/* 28:63 */     setSucursal(sucursal);
/* 29:64 */     setNombreSucursal(nombreSucursal);
/* 30:65 */     setDepartamento(departamento);
/* 31:66 */     setNombreDepartamento(nombreDepartamento);
/* 32:67 */     setCategoriaActivo(categoriaActivo);
/* 33:68 */     setNombreCategoriaActivo(nombreCategoriaActivo);
/* 34:69 */     setSubcategoriaActivo(subcategoriaActivo);
/* 35:70 */     setNombreSubcategoriaActivo(nombreSubcategoriaActivo);
/* 36:71 */     setActivoFijo(activoFijo);
/* 37:72 */     setNombreActivoFijo(nombreActivoFijo);
/* 38:73 */     setAgrupamiento(agrupamiento);
/* 39:74 */     setDescripcion(descripcion);
/* 40:75 */     setValor(valor);
/* 41:76 */     setMotivoBajaActivo(motivoBajaActivo);
/* 42:77 */     setNombreMotivoBajaActivo(nombreMotivoBajaActivo);
/* 43:78 */     DimensionContable centroCosto = new DimensionContable();
/* 44:79 */     if (idCentroCosto != null)
/* 45:   */     {
/* 46:80 */       centroCosto.setIdDimensionContable(idCentroCosto.intValue());
/* 47:81 */       centroCosto.setNombre(nombreCentroCosto);
/* 48:82 */       setDimensionContable1(centroCosto);
/* 49:   */     }
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoBajaActivo
 * JD-Core Version:    0.7.0.1
 */