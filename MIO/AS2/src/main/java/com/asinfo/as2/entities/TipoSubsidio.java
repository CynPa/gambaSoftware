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
/*  14:    */ import javax.validation.constraints.Digits;
/*  15:    */ import javax.validation.constraints.Max;
/*  16:    */ import javax.validation.constraints.Min;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="tipo_subsidio", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"}), @javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"})})
/*  22:    */ public class TipoSubsidio
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -4337960961008481231L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="tipo_subsidio", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_subsidio")
/*  29:    */   @Column(name="id_tipo_subsidio")
/*  30:    */   private int idTipoSubsidio;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="codigo", nullable=false, length=10)
/*  36:    */   @Size(min=2, max=10)
/*  37:    */   private String codigo;
/*  38:    */   @Column(name="nombre", nullable=false, length=50)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=2, max=50)
/*  41:    */   private String nombre;
/*  42:    */   @NotNull
/*  43:    */   @Column(name="porcentaje_subsidio", nullable=true, precision=12, scale=2)
/*  44:    */   @Digits(integer=12, fraction=2)
/*  45:    */   @Min(0L)
/*  46:    */   private BigDecimal porcentajeSubsidio;
/*  47:    */   @Column(name="meses", nullable=true)
/*  48:    */   @Min(0L)
/*  49:    */   private int meses;
/*  50:    */   @Column(name="dias", nullable=true)
/*  51:    */   @Min(0L)
/*  52:    */   private int dias;
/*  53:    */   @Column(name="activo", nullable=false)
/*  54:    */   private boolean activo;
/*  55:    */   @Column(name="predeterminado", nullable=false)
/*  56:    */   private boolean predeterminado;
/*  57:    */   @Column(name="horas_subsidio")
/*  58:    */   @Min(0L)
/*  59:    */   @Max(24L)
/*  60: 96 */   private Integer horasSubsidio = Integer.valueOf(0);
/*  61:    */   @Column(name="indicador_subsidio_vespertino")
/*  62: 99 */   private Boolean indicadorSubsidioVespertino = Boolean.valueOf(true);
/*  63:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  64:    */   @JoinColumn(name="id_rubro", nullable=true)
/*  65:    */   private Rubro rubro;
/*  66:    */   
/*  67:    */   public TipoSubsidio() {}
/*  68:    */   
/*  69:    */   public TipoSubsidio(int idTipoSubsidio, String codigo, String nombre)
/*  70:    */   {
/*  71:121 */     this.idTipoSubsidio = idTipoSubsidio;
/*  72:122 */     this.codigo = codigo;
/*  73:123 */     this.nombre = nombre;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getId()
/*  77:    */   {
/*  78:137 */     return this.idTipoSubsidio;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdTipoSubsidio()
/*  82:    */   {
/*  83:146 */     return this.idTipoSubsidio;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdTipoSubsidio(int idTipoSubsidio)
/*  87:    */   {
/*  88:156 */     this.idTipoSubsidio = idTipoSubsidio;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getIdOrganizacion()
/*  92:    */   {
/*  93:165 */     return this.idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdOrganizacion(int idOrganizacion)
/*  97:    */   {
/*  98:175 */     this.idOrganizacion = idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public int getIdSucursal()
/* 102:    */   {
/* 103:184 */     return this.idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIdSucursal(int idSucursal)
/* 107:    */   {
/* 108:194 */     this.idSucursal = idSucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getNombre()
/* 112:    */   {
/* 113:203 */     return this.nombre;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setNombre(String nombre)
/* 117:    */   {
/* 118:213 */     this.nombre = nombre;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getMeses()
/* 122:    */   {
/* 123:222 */     return this.meses;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setMeses(int meses)
/* 127:    */   {
/* 128:232 */     this.meses = meses;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getDias()
/* 132:    */   {
/* 133:241 */     return this.dias;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setDias(int dias)
/* 137:    */   {
/* 138:251 */     this.dias = dias;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public BigDecimal getPorcentajeSubsidio()
/* 142:    */   {
/* 143:260 */     return this.porcentajeSubsidio;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setPorcentajeSubsidio(BigDecimal porcentajeSubsidio)
/* 147:    */   {
/* 148:270 */     this.porcentajeSubsidio = porcentajeSubsidio;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String getCodigo()
/* 152:    */   {
/* 153:279 */     return this.codigo;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setCodigo(String codigo)
/* 157:    */   {
/* 158:289 */     this.codigo = codigo;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Rubro getRubro()
/* 162:    */   {
/* 163:298 */     return this.rubro;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setRubro(Rubro rubro)
/* 167:    */   {
/* 168:308 */     this.rubro = rubro;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean isActivo()
/* 172:    */   {
/* 173:317 */     return this.activo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setActivo(boolean activo)
/* 177:    */   {
/* 178:327 */     this.activo = activo;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean isPredeterminado()
/* 182:    */   {
/* 183:336 */     return this.predeterminado;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setPredeterminado(boolean predeterminado)
/* 187:    */   {
/* 188:346 */     this.predeterminado = predeterminado;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Integer getHorasSubsidio()
/* 192:    */   {
/* 193:353 */     return Integer.valueOf(this.horasSubsidio == null ? 0 : this.horasSubsidio.intValue());
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setHorasSubsidio(Integer horasSubsidio)
/* 197:    */   {
/* 198:361 */     this.horasSubsidio = horasSubsidio;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public Boolean getIndicadorSubsidioVespertino()
/* 202:    */   {
/* 203:368 */     return Boolean.valueOf(this.indicadorSubsidioVespertino == null ? true : this.indicadorSubsidioVespertino.booleanValue());
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setIndicadorSubsidioVespertino(Boolean indicadorSubsidioVespertino)
/* 207:    */   {
/* 208:375 */     this.indicadorSubsidioVespertino = indicadorSubsidioVespertino;
/* 209:    */   }
/* 210:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoSubsidio
 * JD-Core Version:    0.7.0.1
 */