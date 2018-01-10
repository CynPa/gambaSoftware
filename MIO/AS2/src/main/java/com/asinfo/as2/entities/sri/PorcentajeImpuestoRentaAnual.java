/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.validation.constraints.Digits;
/*  13:    */ import javax.validation.constraints.Max;
/*  14:    */ import javax.validation.constraints.Min;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="porcentaje_impuesto_renta_anual")
/*  18:    */ public class PorcentajeImpuestoRentaAnual
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1843702355590588498L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="porcentaje_impuesto_renta_anual", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="porcentaje_impuesto_renta_anual")
/*  25:    */   @Column(name="id_porcentaje_impuesto_renta_anual")
/*  26:    */   private int idPorcentajeImpuestoRentaAnual;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="anio", nullable=false, length=4)
/*  32:    */   @Min(0L)
/*  33:    */   @Max(9999L)
/*  34:    */   private int anio;
/*  35:    */   @Column(name="porcentaje", nullable=false, scale=2, precision=2)
/*  36:    */   @Digits(integer=2, fraction=2)
/*  37:    */   @Min(0L)
/*  38:    */   @Max(100L)
/*  39:    */   private BigDecimal porcentaje;
/*  40:    */   
/*  41:    */   public int getIdPorcentajeImpuestoRentaAnual()
/*  42:    */   {
/*  43: 91 */     return this.idPorcentajeImpuestoRentaAnual;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setIdPorcentajeImpuestoRentaAnual(int idPorcentajeImpuestoRentaAnual)
/*  47:    */   {
/*  48:101 */     this.idPorcentajeImpuestoRentaAnual = idPorcentajeImpuestoRentaAnual;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getIdOrganizacion()
/*  52:    */   {
/*  53:110 */     return this.idOrganizacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdOrganizacion(int idOrganizacion)
/*  57:    */   {
/*  58:120 */     this.idOrganizacion = idOrganizacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdSucursal()
/*  62:    */   {
/*  63:129 */     return this.idSucursal;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdSucursal(int idSucursal)
/*  67:    */   {
/*  68:139 */     this.idSucursal = idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getAnio()
/*  72:    */   {
/*  73:148 */     return this.anio;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setAnio(int anio)
/*  77:    */   {
/*  78:158 */     this.anio = anio;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public BigDecimal getPorcentaje()
/*  82:    */   {
/*  83:167 */     return this.porcentaje;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setPorcentaje(BigDecimal porcentaje)
/*  87:    */   {
/*  88:177 */     this.porcentaje = porcentaje;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getId()
/*  92:    */   {
/*  93:187 */     return this.idPorcentajeImpuestoRentaAnual;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.PorcentajeImpuestoRentaAnual
 * JD-Core Version:    0.7.0.1
 */