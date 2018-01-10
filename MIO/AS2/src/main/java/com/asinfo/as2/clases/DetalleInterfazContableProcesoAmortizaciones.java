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
/* 13:   */ @Table(name="tmp_detalle_interfaz_contable_proceso_amortizaciones")
/* 14:   */ public class DetalleInterfazContableProcesoAmortizaciones
/* 15:   */   extends DetalleInterfazContableProceso
/* 16:   */ {
/* 17:   */   @Id
/* 18:   */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/* 19:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/* 20:   */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/* 21:   */   private Integer idDetalleInterfazContableProceso;
/* 22:   */   
/* 23:   */   public DetalleInterfazContableProcesoAmortizaciones() {}
/* 24:   */   
/* 25:   */   public DetalleInterfazContableProcesoAmortizaciones(Integer sucursal, String nombreSucursal, Integer tipoAmortizacion, String nombreTipoAmortizacion, String descripcion, BigDecimal valor)
/* 26:   */   {
/* 27:41 */     setSucursal(sucursal);
/* 28:42 */     setNombreSucursal(nombreSucursal);
/* 29:43 */     setTipoAmortizacion(tipoAmortizacion);
/* 30:44 */     setNombreTipoAmortizacion(nombreTipoAmortizacion);
/* 31:45 */     setDescripcion(descripcion);
/* 32:46 */     setValor(valor);
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoAmortizaciones
 * JD-Core Version:    0.7.0.1
 */