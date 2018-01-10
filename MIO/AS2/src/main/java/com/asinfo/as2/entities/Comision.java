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
/*  14:    */ import javax.validation.constraints.Max;
/*  15:    */ import javax.validation.constraints.Min;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="comision")
/*  20:    */ public class Comision
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="comision", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="comision")
/*  27:    */   @Column(name="id_comision")
/*  28:    */   private int idComision;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="descripcion", length=200)
/*  34:    */   @Size(max=200)
/*  35:    */   private String descripcion;
/*  36:    */   @Column(name="activo", nullable=false)
/*  37:    */   private boolean activo;
/*  38:    */   @Column(name="predeterminado", nullable=false)
/*  39:    */   private boolean predeterminado;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_version_comision", nullable=true)
/*  42:    */   private VersionComision versionComision;
/*  43:    */   @Column(name="porcentaje", nullable=true, precision=12, scale=2)
/*  44:    */   @Min(0L)
/*  45:    */   @Max(100L)
/*  46: 68 */   private BigDecimal porcentaje = BigDecimal.ZERO;
/*  47:    */   @Column(name="indicador_iva_sobre_comision", nullable=false)
/*  48:    */   private boolean indicadorIvaSobreComision;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_plan_tarjeta_credito", nullable=true)
/*  51:    */   private PlanTarjetaCredito planTarjetaCredito;
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 82 */     return this.idComision;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdComision()
/*  59:    */   {
/*  60: 86 */     return this.idComision;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdComision(int idComision)
/*  64:    */   {
/*  65: 90 */     this.idComision = idComision;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdOrganizacion()
/*  69:    */   {
/*  70: 94 */     return this.idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdOrganizacion(int idOrganizacion)
/*  74:    */   {
/*  75: 98 */     this.idOrganizacion = idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdSucursal()
/*  79:    */   {
/*  80:102 */     return this.idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdSucursal(int idSucursal)
/*  84:    */   {
/*  85:106 */     this.idSucursal = idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getDescripcion()
/*  89:    */   {
/*  90:110 */     return this.descripcion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setDescripcion(String descripcion)
/*  94:    */   {
/*  95:114 */     this.descripcion = descripcion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isActivo()
/*  99:    */   {
/* 100:118 */     return this.activo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setActivo(boolean activo)
/* 104:    */   {
/* 105:122 */     this.activo = activo;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isPredeterminado()
/* 109:    */   {
/* 110:126 */     return this.predeterminado;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setPredeterminado(boolean predeterminado)
/* 114:    */   {
/* 115:130 */     this.predeterminado = predeterminado;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public PlanTarjetaCredito getPlanTarjetaCredito()
/* 119:    */   {
/* 120:134 */     return this.planTarjetaCredito;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setPlanTarjetaCredito(PlanTarjetaCredito planTarjetaCredito)
/* 124:    */   {
/* 125:138 */     this.planTarjetaCredito = planTarjetaCredito;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public BigDecimal getPorcentaje()
/* 129:    */   {
/* 130:142 */     return this.porcentaje;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setPorcentaje(BigDecimal porcentaje)
/* 134:    */   {
/* 135:146 */     this.porcentaje = porcentaje;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isIndicadorIvaSobreComision()
/* 139:    */   {
/* 140:150 */     return this.indicadorIvaSobreComision;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setIndicadorIvaSobreComision(boolean indicadorIvaSobreComision)
/* 144:    */   {
/* 145:154 */     this.indicadorIvaSobreComision = indicadorIvaSobreComision;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public VersionComision getVersionComision()
/* 149:    */   {
/* 150:158 */     return this.versionComision;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setVersionComision(VersionComision versionComision)
/* 154:    */   {
/* 155:162 */     this.versionComision = versionComision;
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Comision
 * JD-Core Version:    0.7.0.1
 */