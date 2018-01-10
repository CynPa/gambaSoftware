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
/* 13:   */ @Table(name="tmp_detalle_pago_empleado")
/* 14:   */ public class DetallePagoEmpleado
/* 15:   */   extends DetalleInterfazContableProceso
/* 16:   */ {
/* 17:   */   @Id
/* 18:   */   @TableGenerator(name="tmp_detalle_pago_empleado", initialValue=0, allocationSize=50)
/* 19:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tmp_detalle_pago_empleado")
/* 20:   */   @Column(name="id_tmp_pago_empleado")
/* 21:   */   private Integer idDetallePagoEmpleado;
/* 22:   */   
/* 23:   */   public DetallePagoEmpleado() {}
/* 24:   */   
/* 25:   */   public DetallePagoEmpleado(Integer categoriaEmpresa, String nombreCategoriaEmpresa, BigDecimal valor)
/* 26:   */   {
/* 27:49 */     setCategoriaEmpresa(categoriaEmpresa);
/* 28:50 */     setNombreCategoriaEmpresa(nombreCategoriaEmpresa);
/* 29:51 */     setValor(valor);
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetallePagoEmpleado
 * JD-Core Version:    0.7.0.1
 */