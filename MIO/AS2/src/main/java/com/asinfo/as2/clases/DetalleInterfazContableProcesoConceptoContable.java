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
/* 13:   */ @Table(name="tmp_detalle_interfaz_contable_concepto_contable")
/* 14:   */ public class DetalleInterfazContableProcesoConceptoContable
/* 15:   */   extends DetalleInterfazContableProceso
/* 16:   */ {
/* 17:   */   @Id
/* 18:   */   @TableGenerator(name="tmp_detalle_interfaz_contable_proceso", initialValue=0, allocationSize=50)
/* 19:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_interfaz_contable_proceso")
/* 20:   */   @Column(name="id_tmp_detalle_interfaz_contable_proceso")
/* 21:   */   private Integer idDetalleInterfazContableProceso;
/* 22:   */   
/* 23:   */   public DetalleInterfazContableProcesoConceptoContable() {}
/* 24:   */   
/* 25:   */   public DetalleInterfazContableProcesoConceptoContable(Integer documento, Integer sucursal, Integer conceptoContable, String descripcion, BigDecimal valor)
/* 26:   */   {
/* 27:51 */     setDocumento(documento);
/* 28:52 */     setSucursal(sucursal);
/* 29:53 */     setConceptoContable(conceptoContable);
/* 30:54 */     setDescripcion(descripcion);
/* 31:55 */     setValor(valor);
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContableProcesoConceptoContable
 * JD-Core Version:    0.7.0.1
 */