/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="movimiento_bancario_estado_cheque")
/*  18:    */ public class MovimientoBancarioEstadoCheque
/*  19:    */   extends EntidadBase
/*  20:    */   implements Serializable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="movimiento_bancario_estado_cheque", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="movimiento_bancario_estado_cheque")
/*  26:    */   @Column(name="id_movimiento_bancario_estado_cheque")
/*  27:    */   private int idMovimientoBancarioEstadoCheque;
/*  28:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  29:    */   @JoinColumn(name="id_movimiento_bancario", nullable=false)
/*  30:    */   private MovimientoBancario movimientoBancario;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_estado_cheque", nullable=false)
/*  33:    */   private EstadoCheque estadoCheque;
/*  34:    */   @Column(name="observacion", nullable=true, length=300)
/*  35:    */   @Size(max=300)
/*  36:    */   private String observacion;
/*  37:    */   
/*  38:    */   public int getId()
/*  39:    */   {
/*  40: 61 */     return this.idMovimientoBancarioEstadoCheque;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getIdMovimientoBancarioEstadoCheque()
/*  44:    */   {
/*  45: 68 */     return this.idMovimientoBancarioEstadoCheque;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setIdMovimientoBancarioEstadoCheque(int idMovimientoBancarioEstadoCheque)
/*  49:    */   {
/*  50: 76 */     this.idMovimientoBancarioEstadoCheque = idMovimientoBancarioEstadoCheque;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public MovimientoBancario getMovimientoBancario()
/*  54:    */   {
/*  55: 83 */     return this.movimientoBancario;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setMovimientoBancario(MovimientoBancario movimientoBancario)
/*  59:    */   {
/*  60: 91 */     this.movimientoBancario = movimientoBancario;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public EstadoCheque getEstadoCheque()
/*  64:    */   {
/*  65: 98 */     return this.estadoCheque;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setEstadoCheque(EstadoCheque estadoCheque)
/*  69:    */   {
/*  70:106 */     this.estadoCheque = estadoCheque;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getObservacion()
/*  74:    */   {
/*  75:113 */     return this.observacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setObservacion(String observacion)
/*  79:    */   {
/*  80:121 */     this.observacion = observacion;
/*  81:    */   }
/*  82:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MovimientoBancarioEstadoCheque
 * JD-Core Version:    0.7.0.1
 */