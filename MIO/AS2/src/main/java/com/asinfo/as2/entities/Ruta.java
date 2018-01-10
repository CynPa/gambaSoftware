/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.FormaPagoFleteEnum;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.Digits;
/*  18:    */ import javax.validation.constraints.Min;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="ruta")
/*  24:    */ public class Ruta
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="ruta", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ruta")
/*  31:    */   @Column(name="id_ruta")
/*  32:    */   private int idRuta;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  38:    */   @JoinColumn(name="id_ciudad_origen", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private Ciudad ciudadOrigen;
/*  41:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  42:    */   @JoinColumn(name="id_ciudad_destino", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private Ciudad ciudadDestino;
/*  45:    */   @Column(name="tarifa", nullable=false)
/*  46:    */   @Digits(integer=12, fraction=4)
/*  47:    */   @NotNull
/*  48:    */   @Min(0L)
/*  49: 61 */   private BigDecimal tarifa = BigDecimal.ZERO;
/*  50:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  51:    */   @JoinColumn(name="id_tipo_vehiculo", nullable=true)
/*  52:    */   private TipoVehiculo tipoVehiculo;
/*  53:    */   @Column(name="ruta", nullable=true)
/*  54:    */   @Size(max=300)
/*  55: 71 */   private String ruta = "";
/*  56:    */   @Column(name="forma_pago_flete", nullable=false)
/*  57:    */   @Enumerated(EnumType.ORDINAL)
/*  58:    */   @NotNull
/*  59: 75 */   private FormaPagoFleteEnum formaPagoFlete = FormaPagoFleteEnum.PAGA_X_NUMERO_FLETES;
/*  60:    */   
/*  61:    */   public int getId()
/*  62:    */   {
/*  63: 82 */     return this.idRuta;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdRuta()
/*  67:    */   {
/*  68: 86 */     return this.idRuta;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdRuta(int idRuta)
/*  72:    */   {
/*  73: 90 */     this.idRuta = idRuta;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdOrganizacion()
/*  77:    */   {
/*  78: 94 */     return this.idOrganizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdOrganizacion(int idOrganizacion)
/*  82:    */   {
/*  83: 98 */     this.idOrganizacion = idOrganizacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdSucursal()
/*  87:    */   {
/*  88:102 */     return this.idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdSucursal(int idSucursal)
/*  92:    */   {
/*  93:106 */     this.idSucursal = idSucursal;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Ciudad getCiudadOrigen()
/*  97:    */   {
/*  98:110 */     return this.ciudadOrigen;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setCiudadOrigen(Ciudad ciudadOrigen)
/* 102:    */   {
/* 103:114 */     this.ciudadOrigen = ciudadOrigen;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Ciudad getCiudadDestino()
/* 107:    */   {
/* 108:118 */     return this.ciudadDestino;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setCiudadDestino(Ciudad ciudadDestino)
/* 112:    */   {
/* 113:122 */     this.ciudadDestino = ciudadDestino;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public TipoVehiculo getTipoVehiculo()
/* 117:    */   {
/* 118:126 */     return this.tipoVehiculo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setTipoVehiculo(TipoVehiculo tipoVehiculo)
/* 122:    */   {
/* 123:130 */     this.tipoVehiculo = tipoVehiculo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getRuta()
/* 127:    */   {
/* 128:137 */     return this.ruta;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setRuta(String ruta)
/* 132:    */   {
/* 133:145 */     this.ruta = ruta;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public BigDecimal getTarifa()
/* 137:    */   {
/* 138:149 */     if (this.tarifa == null) {
/* 139:150 */       this.tarifa = BigDecimal.ZERO;
/* 140:    */     }
/* 141:152 */     return this.tarifa;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setTarifa(BigDecimal tarifa)
/* 145:    */   {
/* 146:156 */     this.tarifa = tarifa;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public FormaPagoFleteEnum getFormaPagoFlete()
/* 150:    */   {
/* 151:160 */     return this.formaPagoFlete;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setFormaPagoFlete(FormaPagoFleteEnum formaPagoFlete)
/* 155:    */   {
/* 156:164 */     this.formaPagoFlete = formaPagoFlete;
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Ruta
 * JD-Core Version:    0.7.0.1
 */