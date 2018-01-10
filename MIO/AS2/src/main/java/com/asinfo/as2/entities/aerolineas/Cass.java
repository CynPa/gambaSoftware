/*   1:    */ package com.asinfo.as2.entities.aerolineas;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import com.asinfo.as2.entities.GuiaAerea;
/*   7:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.EnumType;
/*  13:    */ import javax.persistence.Enumerated;
/*  14:    */ import javax.persistence.FetchType;
/*  15:    */ import javax.persistence.GeneratedValue;
/*  16:    */ import javax.persistence.GenerationType;
/*  17:    */ import javax.persistence.Id;
/*  18:    */ import javax.persistence.JoinColumn;
/*  19:    */ import javax.persistence.ManyToOne;
/*  20:    */ import javax.persistence.OneToMany;
/*  21:    */ import javax.persistence.Table;
/*  22:    */ import javax.persistence.TableGenerator;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="cass")
/*  28:    */ public class Cass
/*  29:    */   extends EntidadBase
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="cass", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cass")
/*  35:    */   @Column(name="id_cass", unique=true, nullable=false)
/*  36:    */   private int idCass;
/*  37:    */   @Column(name="id_organizacion", nullable=false)
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="id_sucursal", nullable=false)
/*  40:    */   private int idSucursal;
/*  41:    */   @Column(name="record_id", nullable=true, length=50)
/*  42:    */   private String recordId;
/*  43:    */   @Column(name="cass_area_code", nullable=true, length=50)
/*  44:    */   private String cassAreaCode;
/*  45:    */   @Column(name="airline_prefix", nullable=true)
/*  46:    */   private Integer airlinePrefix;
/*  47:    */   @Column(name="date_period_start", nullable=true)
/*  48:    */   private Integer datePeriodStart;
/*  49:    */   @Column(name="date_period_end", nullable=true)
/*  50:    */   private Integer datePeriodEnd;
/*  51:    */   @Column(name="date_of_billing", nullable=true)
/*  52:    */   private Integer dateOfBilling;
/*  53:    */   @Column(name="file_number", nullable=true, length=200)
/*  54:    */   private Integer fileNumber;
/*  55:    */   @Column(name="currency_code", nullable=true, length=50)
/*  56:    */   private String currencyCode;
/*  57:    */   @Column(name="branch_office_indicator", nullable=true, length=50)
/*  58:    */   private String branchOfficeIndicator;
/*  59:    */   @Column(name="filler", nullable=true, length=50)
/*  60:    */   private String filler;
/*  61:    */   @Column(name="reserved_space", nullable=true, length=100)
/*  62:    */   private String reservedSpace;
/*  63:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  64:    */   @JoinColumn(name="id_asiento", nullable=true)
/*  65:    */   private Asiento asiento;
/*  66:    */   @Enumerated(EnumType.STRING)
/*  67:    */   @Column(name="estado", length=50, nullable=true)
/*  68:    */   private Estado estado;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_documento", nullable=false)
/*  71:    */   @NotNull
/*  72:    */   private Documento documento;
/*  73:    */   @Column(name="numero", nullable=false, length=20)
/*  74:    */   @NotNull
/*  75:    */   @Size(max=20)
/*  76:    */   private String numero;
/*  77:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="cass", cascade={javax.persistence.CascadeType.DETACH})
/*  78:103 */   private List<GuiaAerea> listaGuiasAereas = new ArrayList();
/*  79:    */   
/*  80:    */   public int getId()
/*  81:    */   {
/*  82:108 */     return this.idCass;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String toString()
/*  86:    */   {
/*  87:113 */     return this.recordId;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getRecordId()
/*  91:    */   {
/*  92:117 */     return this.recordId;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setRecordId(String recordId)
/*  96:    */   {
/*  97:121 */     this.recordId = recordId;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getCassAreaCode()
/* 101:    */   {
/* 102:125 */     return this.cassAreaCode;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setCassAreaCode(String cassAreaCode)
/* 106:    */   {
/* 107:129 */     this.cassAreaCode = cassAreaCode;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Integer getAirlinePrefix()
/* 111:    */   {
/* 112:133 */     return this.airlinePrefix;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setAirlinePrefix(Integer airlinePrefix)
/* 116:    */   {
/* 117:137 */     this.airlinePrefix = airlinePrefix;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getIdCass()
/* 121:    */   {
/* 122:141 */     return this.idCass;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setIdCass(int idCass)
/* 126:    */   {
/* 127:145 */     this.idCass = idCass;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Integer getDatePeriodStart()
/* 131:    */   {
/* 132:149 */     return this.datePeriodStart;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setDatePeriodStart(Integer datePeriodStart)
/* 136:    */   {
/* 137:153 */     this.datePeriodStart = datePeriodStart;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Integer getDatePeriodEnd()
/* 141:    */   {
/* 142:157 */     return this.datePeriodEnd;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setDatePeriodEnd(Integer datePeriodEnd)
/* 146:    */   {
/* 147:161 */     this.datePeriodEnd = datePeriodEnd;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Integer getDateOfBilling()
/* 151:    */   {
/* 152:165 */     return this.dateOfBilling;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setDateOfBilling(Integer dateOfBilling)
/* 156:    */   {
/* 157:169 */     this.dateOfBilling = dateOfBilling;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Integer getFileNumber()
/* 161:    */   {
/* 162:173 */     return this.fileNumber;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setFileNumber(Integer fileNumber)
/* 166:    */   {
/* 167:177 */     this.fileNumber = fileNumber;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getCurrencyCode()
/* 171:    */   {
/* 172:181 */     return this.currencyCode;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setCurrencyCode(String currencyCode)
/* 176:    */   {
/* 177:185 */     this.currencyCode = currencyCode;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getBranchOfficeIndicator()
/* 181:    */   {
/* 182:189 */     return this.branchOfficeIndicator;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setBranchOfficeIndicator(String branchOfficeIndicator)
/* 186:    */   {
/* 187:193 */     this.branchOfficeIndicator = branchOfficeIndicator;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String getFiller()
/* 191:    */   {
/* 192:197 */     return this.filler;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setFiller(String filler)
/* 196:    */   {
/* 197:201 */     this.filler = filler;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String getReservedSpace()
/* 201:    */   {
/* 202:205 */     return this.reservedSpace;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setReservedSpace(String reservedSpace)
/* 206:    */   {
/* 207:209 */     this.reservedSpace = reservedSpace;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public List<GuiaAerea> getListaGuiasAereas()
/* 211:    */   {
/* 212:213 */     return this.listaGuiasAereas;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setListaGuiasAereas(List<GuiaAerea> listaGuiasAereas)
/* 216:    */   {
/* 217:217 */     this.listaGuiasAereas = listaGuiasAereas;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public int getIdOrganizacion()
/* 221:    */   {
/* 222:221 */     return this.idOrganizacion;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setIdOrganizacion(int idOrganizacion)
/* 226:    */   {
/* 227:225 */     this.idOrganizacion = idOrganizacion;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public int getIdSucursal()
/* 231:    */   {
/* 232:229 */     return this.idSucursal;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setIdSucursal(int idSucursal)
/* 236:    */   {
/* 237:233 */     this.idSucursal = idSucursal;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Asiento getAsiento()
/* 241:    */   {
/* 242:237 */     return this.asiento;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setAsiento(Asiento asiento)
/* 246:    */   {
/* 247:241 */     this.asiento = asiento;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public Estado getEstado()
/* 251:    */   {
/* 252:245 */     return this.estado;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setEstado(Estado estado)
/* 256:    */   {
/* 257:249 */     this.estado = estado;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public Documento getDocumento()
/* 261:    */   {
/* 262:253 */     return this.documento;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setDocumento(Documento documento)
/* 266:    */   {
/* 267:257 */     this.documento = documento;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public String getNumero()
/* 271:    */   {
/* 272:261 */     return this.numero;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setNumero(String numero)
/* 276:    */   {
/* 277:265 */     this.numero = numero;
/* 278:    */   }
/* 279:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.aerolineas.Cass
 * JD-Core Version:    0.7.0.1
 */