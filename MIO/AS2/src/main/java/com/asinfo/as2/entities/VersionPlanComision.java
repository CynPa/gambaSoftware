/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoComisionEnum;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="version_plan_comision", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_plan_comision", "codigo"})})
/*  26:    */ public class VersionPlanComision
/*  27:    */   extends EntidadBase
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="version_plan_comision", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="version_plan_comision")
/*  33:    */   @Column(name="id_version_plan_comision")
/*  34:    */   private int idVersionPlanComision;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   private int idSucursal;
/*  39:    */   @Column(name="codigo", nullable=false, length=10)
/*  40:    */   @Size(min=2, max=10)
/*  41:    */   private String codigo;
/*  42:    */   @Column(name="nombre", nullable=false, length=50)
/*  43:    */   @Size(min=2, max=50)
/*  44:    */   private String nombre;
/*  45:    */   @Column(name="descripcion", nullable=true, length=200)
/*  46:    */   @Size(max=200)
/*  47:    */   private String descripcion;
/*  48:    */   @Column(name="activo", nullable=false)
/*  49:    */   private boolean activo;
/*  50:    */   @Column(name="predeterminado", nullable=false)
/*  51:    */   private boolean predeterminado;
/*  52:    */   @Column(name="tipo_comision_enum", nullable=false)
/*  53:    */   @Enumerated(EnumType.ORDINAL)
/*  54:    */   @NotNull
/*  55:    */   private TipoComisionEnum tipoComisionEnum;
/*  56:    */   @Column(name="mes_inicial", nullable=false)
/*  57:    */   @Enumerated(EnumType.ORDINAL)
/*  58:    */   @NotNull
/*  59:    */   private Mes mesInicial;
/*  60:    */   @Column(name="mes_final", nullable=false)
/*  61:    */   @Enumerated(EnumType.ORDINAL)
/*  62:    */   @NotNull
/*  63:    */   private Mes mesFinal;
/*  64:    */   @Column(name="anio_inicial", nullable=false)
/*  65:    */   @NotNull
/*  66:    */   private int anioInicial;
/*  67:    */   @Column(name="anio_final", nullable=false)
/*  68:    */   @NotNull
/*  69:    */   private int anioFinal;
/*  70:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  71:    */   @JoinColumn(name="id_plan_comision", nullable=false)
/*  72:    */   @NotNull
/*  73:    */   private PlanComision planComision;
/*  74:    */   @OneToMany(cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.LAZY, mappedBy="versionPlanComision")
/*  75:115 */   private List<DetalleVersionPlanComision> listaDetalleVersionPlanComision = new ArrayList();
/*  76:    */   @OneToMany(cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.LAZY, mappedBy="versionPlanComision")
/*  77:118 */   private List<DetalleVersionPlanComisionSupervisor> listaDetalleVersionPlanComisionSupervisor = new ArrayList();
/*  78:    */   @Transient
/*  79:125 */   private List<DetalleVersionPlanComisionRangoDias> listaDetalleVersionPlanComisionRangoDias = new ArrayList();
/*  80:    */   
/*  81:    */   public int getId()
/*  82:    */   {
/*  83:141 */     return this.idVersionPlanComision;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdVersionPlanComision()
/*  87:    */   {
/*  88:145 */     return this.idVersionPlanComision;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdVersionPlanComision(int idVersionPlanComision)
/*  92:    */   {
/*  93:149 */     this.idVersionPlanComision = idVersionPlanComision;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdOrganizacion()
/*  97:    */   {
/*  98:153 */     return this.idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdOrganizacion(int idOrganizacion)
/* 102:    */   {
/* 103:157 */     this.idOrganizacion = idOrganizacion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getIdSucursal()
/* 107:    */   {
/* 108:161 */     return this.idSucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdSucursal(int idSucursal)
/* 112:    */   {
/* 113:165 */     this.idSucursal = idSucursal;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getCodigo()
/* 117:    */   {
/* 118:169 */     return this.codigo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setCodigo(String codigo)
/* 122:    */   {
/* 123:173 */     this.codigo = codigo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getNombre()
/* 127:    */   {
/* 128:177 */     return this.nombre;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setNombre(String nombre)
/* 132:    */   {
/* 133:181 */     this.nombre = nombre;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String getDescripcion()
/* 137:    */   {
/* 138:185 */     return this.descripcion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDescripcion(String descripcion)
/* 142:    */   {
/* 143:189 */     this.descripcion = descripcion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isActivo()
/* 147:    */   {
/* 148:193 */     return this.activo;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setActivo(boolean activo)
/* 152:    */   {
/* 153:197 */     this.activo = activo;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isPredeterminado()
/* 157:    */   {
/* 158:201 */     return this.predeterminado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setPredeterminado(boolean predeterminado)
/* 162:    */   {
/* 163:205 */     this.predeterminado = predeterminado;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public TipoComisionEnum getTipoComisionEnum()
/* 167:    */   {
/* 168:209 */     return this.tipoComisionEnum;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setTipoComisionEnum(TipoComisionEnum tipoComisionEnum)
/* 172:    */   {
/* 173:213 */     this.tipoComisionEnum = tipoComisionEnum;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Mes getMesInicial()
/* 177:    */   {
/* 178:217 */     return this.mesInicial;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setMesInicial(Mes mesInicial)
/* 182:    */   {
/* 183:221 */     this.mesInicial = mesInicial;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Mes getMesFinal()
/* 187:    */   {
/* 188:225 */     return this.mesFinal;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setMesFinal(Mes mesFinal)
/* 192:    */   {
/* 193:229 */     this.mesFinal = mesFinal;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public int getAnioInicial()
/* 197:    */   {
/* 198:233 */     return this.anioInicial;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setAnioInicial(int anioInicial)
/* 202:    */   {
/* 203:237 */     this.anioInicial = anioInicial;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public int getAnioFinal()
/* 207:    */   {
/* 208:241 */     return this.anioFinal;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setAnioFinal(int anioFinal)
/* 212:    */   {
/* 213:245 */     this.anioFinal = anioFinal;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public PlanComision getPlanComision()
/* 217:    */   {
/* 218:249 */     return this.planComision;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setPlanComision(PlanComision planComision)
/* 222:    */   {
/* 223:253 */     this.planComision = planComision;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public List<DetalleVersionPlanComision> getListaDetalleVersionPlanComision()
/* 227:    */   {
/* 228:257 */     return this.listaDetalleVersionPlanComision;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setListaDetalleVersionPlanComision(List<DetalleVersionPlanComision> listaDetalleVersionPlanComision)
/* 232:    */   {
/* 233:261 */     this.listaDetalleVersionPlanComision = listaDetalleVersionPlanComision;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public List<DetalleVersionPlanComisionSupervisor> getListaDetalleVersionPlanComisionSupervisor()
/* 237:    */   {
/* 238:265 */     return this.listaDetalleVersionPlanComisionSupervisor;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setListaDetalleVersionPlanComisionSupervisor(List<DetalleVersionPlanComisionSupervisor> listaDetalleVersionPlanComisionSupervisor)
/* 242:    */   {
/* 243:269 */     this.listaDetalleVersionPlanComisionSupervisor = listaDetalleVersionPlanComisionSupervisor;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public List<DetalleVersionPlanComisionRangoDias> getListaDetalleVersionPlanComisionRangoDias()
/* 247:    */   {
/* 248:273 */     return this.listaDetalleVersionPlanComisionRangoDias;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setListaDetalleVersionPlanComisionRangoDias(List<DetalleVersionPlanComisionRangoDias> listaDetalleVersionPlanComisionRangoDias)
/* 252:    */   {
/* 253:277 */     this.listaDetalleVersionPlanComisionRangoDias = listaDetalleVersionPlanComisionRangoDias;
/* 254:    */   }
/* 255:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.VersionPlanComision
 * JD-Core Version:    0.7.0.1
 */