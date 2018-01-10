/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.Digits;
/*  12:    */ import javax.validation.constraints.Max;
/*  13:    */ import javax.validation.constraints.Min;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="impuesto_rentaSRI")
/*  17:    */ public class ImpuestoRentaSRI
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = -4417092384695341787L;
/*  21:    */   @Column(name="id_organizacion", nullable=false)
/*  22:    */   private int idOrganizacion;
/*  23:    */   @Column(name="id_sucursal", nullable=false)
/*  24:    */   private int idSucursal;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="impuesto_rentaSRI", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="impuesto_rentaSRI")
/*  28:    */   @Column(name="id_impuesto_rentaSRI")
/*  29:    */   private int idImpuestoRentaSRI;
/*  30:    */   @Column(name="anio", nullable=false, length=4)
/*  31:    */   @Min(0L)
/*  32:    */   @Max(9999L)
/*  33:    */   private int anio;
/*  34:    */   @Column(name="desde", nullable=false, scale=12, precision=2)
/*  35:    */   @Digits(integer=12, fraction=2)
/*  36:    */   @Min(0L)
/*  37:    */   private BigDecimal desde;
/*  38:    */   @Column(name="hasta", nullable=false, scale=12, precision=2)
/*  39:    */   @Digits(integer=12, fraction=2)
/*  40:    */   @Min(0L)
/*  41:    */   private BigDecimal hasta;
/*  42:    */   @Column(name="fraccion_basica", nullable=false, scale=12, precision=2)
/*  43:    */   @Digits(integer=12, fraction=2)
/*  44:    */   @Min(0L)
/*  45:    */   private BigDecimal fraccionBasica;
/*  46:    */   @Column(name="porcentaje", nullable=false, scale=12, precision=2)
/*  47:    */   @Digits(integer=12, fraction=2)
/*  48:    */   @Min(0L)
/*  49:    */   private BigDecimal porcentaje;
/*  50:    */   @Column(name="activo", nullable=false)
/*  51:    */   private boolean activo;
/*  52:    */   @Column(name="predeterminado", nullable=false)
/*  53:    */   private boolean predeterminado;
/*  54:    */   
/*  55:    */   public int getIdOrganizacion()
/*  56:    */   {
/*  57:113 */     return this.idOrganizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdOrganizacion(int idOrganizacion)
/*  61:    */   {
/*  62:123 */     this.idOrganizacion = idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdSucursal()
/*  66:    */   {
/*  67:132 */     return this.idSucursal;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdSucursal(int idSucursal)
/*  71:    */   {
/*  72:142 */     this.idSucursal = idSucursal;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdImpuestoRentaSRI()
/*  76:    */   {
/*  77:151 */     return this.idImpuestoRentaSRI;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdImpuestoRentaSRI(int idImpuestoRentaSRI)
/*  81:    */   {
/*  82:161 */     this.idImpuestoRentaSRI = idImpuestoRentaSRI;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getAnio()
/*  86:    */   {
/*  87:170 */     return this.anio;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setAnio(int anio)
/*  91:    */   {
/*  92:180 */     this.anio = anio;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public BigDecimal getDesde()
/*  96:    */   {
/*  97:189 */     return this.desde;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setDesde(BigDecimal desde)
/* 101:    */   {
/* 102:199 */     this.desde = desde;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public BigDecimal getHasta()
/* 106:    */   {
/* 107:208 */     return this.hasta;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setHasta(BigDecimal hasta)
/* 111:    */   {
/* 112:218 */     this.hasta = hasta;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public BigDecimal getFraccionBasica()
/* 116:    */   {
/* 117:227 */     return this.fraccionBasica;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setFraccionBasica(BigDecimal fraccionBasica)
/* 121:    */   {
/* 122:237 */     this.fraccionBasica = fraccionBasica;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public BigDecimal getPorcentaje()
/* 126:    */   {
/* 127:246 */     return this.porcentaje;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setPorcentaje(BigDecimal porcentaje)
/* 131:    */   {
/* 132:256 */     this.porcentaje = porcentaje;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getId()
/* 136:    */   {
/* 137:266 */     return this.idImpuestoRentaSRI;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean isActivo()
/* 141:    */   {
/* 142:275 */     return this.activo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setActivo(boolean activo)
/* 146:    */   {
/* 147:285 */     this.activo = activo;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isPredeterminado()
/* 151:    */   {
/* 152:294 */     return this.predeterminado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setPredeterminado(boolean predeterminado)
/* 156:    */   {
/* 157:304 */     this.predeterminado = predeterminado;
/* 158:    */   }
/* 159:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ImpuestoRentaSRI
 * JD-Core Version:    0.7.0.1
 */