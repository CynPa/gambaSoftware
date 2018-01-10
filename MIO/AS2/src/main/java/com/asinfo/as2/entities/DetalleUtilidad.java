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
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="detalle_utilidad", uniqueConstraints={})
/*  20:    */ public class DetalleUtilidad
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="detalle_utilidad", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_utilidad")
/*  27:    */   @Column(name="id_detalle_utilidad")
/*  28:    */   private int idDetalleUtilidad;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="valor10", nullable=false)
/*  34: 57 */   private BigDecimal valor10 = BigDecimal.ZERO;
/*  35:    */   @Column(name="valor5", nullable=false)
/*  36: 60 */   private BigDecimal valor5 = BigDecimal.ZERO;
/*  37:    */   @Column(name="cargas_familiares", nullable=false)
/*  38:    */   @Min(0L)
/*  39:    */   private int cargasFamiliares;
/*  40:    */   @NotNull
/*  41:    */   @Column(name="dias_trabajados", nullable=false)
/*  42:    */   @Min(0L)
/*  43: 67 */   private BigDecimal diasTrabajados = BigDecimal.ZERO;
/*  44:    */   @NotNull
/*  45:    */   @Column(name="dias_reales_trabajados", nullable=false)
/*  46:    */   @Min(0L)
/*  47: 72 */   private BigDecimal diasRealesTrabajados = BigDecimal.ZERO;
/*  48:    */   @NotNull
/*  49:    */   @Column(name="retencion_judicial", nullable=false)
/*  50:    */   @Min(0L)
/*  51: 77 */   private BigDecimal retencionJudicial = BigDecimal.ZERO;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_utilidad", nullable=true)
/*  54:    */   private Utilidad utilidad;
/*  55:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  56:    */   @JoinColumn(name="id_empleado", nullable=true)
/*  57:    */   private Empleado empleado;
/*  58:    */   @Column(name="identificacion", nullable=true, length=20)
/*  59:    */   @Size(min=2, max=20)
/*  60:    */   private String identificacion;
/*  61:    */   @Column(name="nombres", nullable=true, length=100)
/*  62:    */   @Size(min=1, max=100)
/*  63:    */   private String nombres;
/*  64:    */   @Column(name="apellidos", nullable=true, length=100)
/*  65:    */   @Size(min=1, max=100)
/*  66:    */   private String apellidos;
/*  67:    */   @Column(name="ruc_empresa_complementaria", nullable=true, length=20)
/*  68:    */   private String rucEmpresaComplementaria;
/*  69:    */   @Column(name="codigo_sectorial", nullable=true, length=20)
/*  70:    */   @Size(max=20)
/*  71:    */   private String codigoSectorial;
/*  72:    */   
/*  73:    */   public int getIdDetalleUtilidad()
/*  74:    */   {
/*  75:132 */     return this.idDetalleUtilidad;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdDetalleUtilidad(int idDetalleUtilidad)
/*  79:    */   {
/*  80:142 */     this.idDetalleUtilidad = idDetalleUtilidad;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdOrganizacion()
/*  84:    */   {
/*  85:151 */     return this.idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdOrganizacion(int idOrganizacion)
/*  89:    */   {
/*  90:161 */     this.idOrganizacion = idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getIdSucursal()
/*  94:    */   {
/*  95:170 */     return this.idSucursal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdSucursal(int idSucursal)
/*  99:    */   {
/* 100:180 */     this.idSucursal = idSucursal;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public BigDecimal getValor10()
/* 104:    */   {
/* 105:189 */     return this.valor10;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setValor10(BigDecimal valor10)
/* 109:    */   {
/* 110:199 */     this.valor10 = valor10;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public BigDecimal getValor5()
/* 114:    */   {
/* 115:208 */     return this.valor5;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setValor5(BigDecimal valor5)
/* 119:    */   {
/* 120:218 */     this.valor5 = valor5;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Utilidad getUtilidad()
/* 124:    */   {
/* 125:227 */     return this.utilidad;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setUtilidad(Utilidad utilidad)
/* 129:    */   {
/* 130:237 */     this.utilidad = utilidad;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Empleado getEmpleado()
/* 134:    */   {
/* 135:246 */     return this.empleado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setEmpleado(Empleado empleado)
/* 139:    */   {
/* 140:256 */     this.empleado = empleado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int getCargasFamiliares()
/* 144:    */   {
/* 145:265 */     return this.cargasFamiliares;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setCargasFamiliares(int cargasFamiliares)
/* 149:    */   {
/* 150:275 */     this.cargasFamiliares = cargasFamiliares;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public BigDecimal getDiasTrabajados()
/* 154:    */   {
/* 155:284 */     return this.diasTrabajados;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setDiasTrabajados(BigDecimal diasTrabajados)
/* 159:    */   {
/* 160:294 */     this.diasTrabajados = diasTrabajados;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public int getId()
/* 164:    */   {
/* 165:304 */     return this.idDetalleUtilidad;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getIdentificacion()
/* 169:    */   {
/* 170:311 */     return this.identificacion;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIdentificacion(String identificacion)
/* 174:    */   {
/* 175:319 */     this.identificacion = identificacion;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getNombres()
/* 179:    */   {
/* 180:326 */     return this.nombres;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setNombres(String nombres)
/* 184:    */   {
/* 185:334 */     this.nombres = nombres;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String getApellidos()
/* 189:    */   {
/* 190:341 */     return this.apellidos;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setApellidos(String apellidos)
/* 194:    */   {
/* 195:349 */     this.apellidos = apellidos;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public BigDecimal getDiasRealesTrabajados()
/* 199:    */   {
/* 200:356 */     return this.diasRealesTrabajados;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setDiasRealesTrabajados(BigDecimal diasRealesTrabajados)
/* 204:    */   {
/* 205:364 */     this.diasRealesTrabajados = diasRealesTrabajados;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public BigDecimal getRetencionJudicial()
/* 209:    */   {
/* 210:371 */     return this.retencionJudicial;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setRetencionJudicial(BigDecimal retencionJudicial)
/* 214:    */   {
/* 215:379 */     this.retencionJudicial = retencionJudicial;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String getRucEmpresaComplementaria()
/* 219:    */   {
/* 220:383 */     return this.rucEmpresaComplementaria;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setRucEmpresaComplementaria(String rucEmpresaComplementaria)
/* 224:    */   {
/* 225:387 */     this.rucEmpresaComplementaria = rucEmpresaComplementaria;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String getCodigoSectorial()
/* 229:    */   {
/* 230:391 */     return this.codigoSectorial;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setCodigoSectorial(String codigoSectorial)
/* 234:    */   {
/* 235:395 */     this.codigoSectorial = codigoSectorial;
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleUtilidad
 * JD-Core Version:    0.7.0.1
 */