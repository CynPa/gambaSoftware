/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="anuladoSRI")
/*  22:    */ public class AnuladoSRI
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 5167206483008194297L;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="anuladoSRI", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="anuladoSRI")
/*  33:    */   @Column(name="id_anuladoSRI")
/*  34:    */   private int idAnuladoSRI;
/*  35:    */   @Column(name="anio", nullable=false)
/*  36:    */   private int anio;
/*  37:    */   @Column(name="mes", nullable=false)
/*  38:    */   private int mes;
/*  39:    */   @Column(name="establecimiento", length=3, nullable=false)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=3, max=3)
/*  42:    */   private String establecimiento;
/*  43:    */   @Column(name="punto_emision", length=3, nullable=false)
/*  44:    */   @NotNull
/*  45:    */   @Size(min=3, max=3)
/*  46:    */   private String puntoEmision;
/*  47:    */   @Column(name="numero_desde", length=20, nullable=false)
/*  48:    */   @NotNull
/*  49:    */   @Size(min=1, max=20)
/*  50:    */   private String numeroDesde;
/*  51:    */   @Column(name="numero_hasta", length=20, nullable=false)
/*  52:    */   @NotNull
/*  53:    */   @Size(min=1, max=20)
/*  54:    */   private String numeroHasta;
/*  55:    */   @Column(name="autorizacion", length=49, nullable=false)
/*  56:    */   @NotNull
/*  57:    */   @Size(min=10, max=49)
/*  58:    */   private String autorizacion;
/*  59:    */   @Column(name="numero_registros_anulados", nullable=true)
/*  60:    */   private Integer numeroRegistrosAnulados;
/*  61:    */   @Column(name="documento_relacionado", nullable=true)
/*  62:    */   private Integer documentoRelacionado;
/*  63:    */   @Temporal(TemporalType.DATE)
/*  64:    */   @Column(name="fecha_emision_documento", nullable=true)
/*  65:    */   private Date fechaEmisionDocumento;
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_tipo_comprobante", nullable=true)
/*  68:    */   private TipoComprobanteSRI tipoComprobanteSRI;
/*  69:    */   
/*  70:    */   public int getId()
/*  71:    */   {
/*  72:133 */     return this.idAnuladoSRI;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdSucursal()
/*  76:    */   {
/*  77:142 */     return this.idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdSucursal(int idSucursal)
/*  81:    */   {
/*  82:152 */     this.idSucursal = idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdAnuladoSRI()
/*  86:    */   {
/*  87:161 */     return this.idAnuladoSRI;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdAnuladoSRI(int idAnuladoSRI)
/*  91:    */   {
/*  92:171 */     this.idAnuladoSRI = idAnuladoSRI;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getAnio()
/*  96:    */   {
/*  97:180 */     return this.anio;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setAnio(int anio)
/* 101:    */   {
/* 102:190 */     this.anio = anio;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getMes()
/* 106:    */   {
/* 107:199 */     return this.mes;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setMes(int mes)
/* 111:    */   {
/* 112:209 */     this.mes = mes;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getEstablecimiento()
/* 116:    */   {
/* 117:218 */     return this.establecimiento;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setEstablecimiento(String establecimiento)
/* 121:    */   {
/* 122:228 */     this.establecimiento = establecimiento;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getPuntoEmision()
/* 126:    */   {
/* 127:237 */     return this.puntoEmision;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setPuntoEmision(String puntoEmision)
/* 131:    */   {
/* 132:247 */     this.puntoEmision = puntoEmision;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getNumeroDesde()
/* 136:    */   {
/* 137:256 */     return this.numeroDesde;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setNumeroDesde(String numeroDesde)
/* 141:    */   {
/* 142:266 */     this.numeroDesde = numeroDesde;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getNumeroHasta()
/* 146:    */   {
/* 147:275 */     return this.numeroHasta;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setNumeroHasta(String numeroHasta)
/* 151:    */   {
/* 152:285 */     this.numeroHasta = numeroHasta;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getAutorizacion()
/* 156:    */   {
/* 157:294 */     return this.autorizacion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setAutorizacion(String autorizacion)
/* 161:    */   {
/* 162:304 */     this.autorizacion = autorizacion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public TipoComprobanteSRI getTipoComprobanteSRI()
/* 166:    */   {
/* 167:313 */     return this.tipoComprobanteSRI;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setTipoComprobanteSRI(TipoComprobanteSRI tipoComprobanteSRI)
/* 171:    */   {
/* 172:323 */     this.tipoComprobanteSRI = tipoComprobanteSRI;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public int getIdOrganizacion()
/* 176:    */   {
/* 177:332 */     return this.idOrganizacion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setIdOrganizacion(int idOrganizacion)
/* 181:    */   {
/* 182:342 */     this.idOrganizacion = idOrganizacion;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Integer getNumeroRegistrosAnulados()
/* 186:    */   {
/* 187:351 */     return this.numeroRegistrosAnulados;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setNumeroRegistrosAnulados(Integer numeroRegistrosAnulados)
/* 191:    */   {
/* 192:361 */     this.numeroRegistrosAnulados = numeroRegistrosAnulados;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public Integer getDocumentoRelacionado()
/* 196:    */   {
/* 197:368 */     return this.documentoRelacionado;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setDocumentoRelacionado(Integer documentoRelacionado)
/* 201:    */   {
/* 202:376 */     this.documentoRelacionado = documentoRelacionado;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public Date getFechaEmisionDocumento()
/* 206:    */   {
/* 207:383 */     return this.fechaEmisionDocumento;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setFechaEmisionDocumento(Date fechaEmisionDocumento)
/* 211:    */   {
/* 212:391 */     this.fechaEmisionDocumento = fechaEmisionDocumento;
/* 213:    */   }
/* 214:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.AnuladoSRI
 * JD-Core Version:    0.7.0.1
 */