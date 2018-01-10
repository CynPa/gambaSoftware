/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
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
/*  14:    */ import javax.validation.constraints.Min;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="detalle_compra_caja_chica_centro_costo")
/*  18:    */ public class DetalleCompraCajaChicaCentroCosto
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="detalle_compra_caja_chica_centro_costo", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_compra_caja_chica_centro_costo")
/*  25:    */   @Column(name="id_detalle_compra_caja_chica_centro_costo")
/*  26:    */   private int idDetalleCompraCajaChicaCentroCosto;
/*  27:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  28:    */   @JoinColumn(name="id_detalle_compra_caja_chica", nullable=true)
/*  29:    */   private DetalleCompraCajaChica detalleCompraCajaChica;
/*  30:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  31:    */   @JoinColumn(name="id_centro_costo", nullable=false)
/*  32:    */   private CentroCosto centroCosto;
/*  33:    */   @Column(name="valor", nullable=true, precision=12, scale=2)
/*  34:    */   @Min(0L)
/*  35: 53 */   private BigDecimal valor = BigDecimal.ZERO;
/*  36:    */   
/*  37:    */   public int getId()
/*  38:    */   {
/*  39: 66 */     return this.idDetalleCompraCajaChicaCentroCosto;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getIdDetalleCompraCajaChicaCentroCosto()
/*  43:    */   {
/*  44: 74 */     return this.idDetalleCompraCajaChicaCentroCosto;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setIdDetalleCompraCajaChicaCentroCosto(int idDetalleCompraCajaChicaCentroCosto)
/*  48:    */   {
/*  49: 83 */     this.idDetalleCompraCajaChicaCentroCosto = idDetalleCompraCajaChicaCentroCosto;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public DetalleCompraCajaChica getDetalleCompraCajaChica()
/*  53:    */   {
/*  54: 91 */     return this.detalleCompraCajaChica;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setDetalleCompraCajaChica(DetalleCompraCajaChica detalleCompraCajaChica)
/*  58:    */   {
/*  59:100 */     this.detalleCompraCajaChica = detalleCompraCajaChica;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public BigDecimal getValor()
/*  63:    */   {
/*  64:108 */     return this.valor;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setValor(BigDecimal valor)
/*  68:    */   {
/*  69:116 */     this.valor = valor;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public CentroCosto getCentroCosto()
/*  73:    */   {
/*  74:120 */     return this.centroCosto;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setCentroCosto(CentroCosto centroCosto)
/*  78:    */   {
/*  79:124 */     this.centroCosto = centroCosto;
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleCompraCajaChicaCentroCosto
 * JD-Core Version:    0.7.0.1
 */