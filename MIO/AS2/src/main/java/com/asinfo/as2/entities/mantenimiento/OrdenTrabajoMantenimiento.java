/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Documento;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
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
/*  23:    */ import javax.persistence.Temporal;
/*  24:    */ import javax.persistence.TemporalType;
/*  25:    */ import javax.validation.constraints.NotNull;
/*  26:    */ import javax.validation.constraints.Size;
/*  27:    */ import org.hibernate.annotations.ColumnDefault;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="orden_trabajo_mantenimiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_documento", "numero"})})
/*  31:    */ public class OrdenTrabajoMantenimiento
/*  32:    */   extends EntidadBase
/*  33:    */   implements Serializable
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 1L;
/*  36:    */   @Id
/*  37:    */   @TableGenerator(name="orden_trabajo_mantenimiento", initialValue=0, allocationSize=50)
/*  38:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="orden_trabajo_mantenimiento")
/*  39:    */   @Column(name="id_orden_trabajo_mantenimiento")
/*  40:    */   private int idOrdenTrabajoMantenimiento;
/*  41:    */   @Column(name="id_organizacion")
/*  42:    */   private int idOrganizacion;
/*  43:    */   @Column(name="id_sucursal")
/*  44:    */   private int idSucursal;
/*  45:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  46:    */   @JoinColumn(name="id_documento", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private Documento documento;
/*  49:    */   @Column(name="numero", nullable=false, length=20)
/*  50:    */   @NotNull
/*  51:    */   @Size(max=20)
/*  52:    */   private String numero;
/*  53:    */   @Temporal(TemporalType.DATE)
/*  54:    */   @Column(name="fecha_mantenimiento", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private Date fechaMantenimiento;
/*  57:    */   @Temporal(TemporalType.DATE)
/*  58:    */   @Column(name="fecha_cierre", nullable=true)
/*  59:    */   private Date fechaCierre;
/*  60:    */   @Column(name="descripcion", length=200, nullable=true)
/*  61:    */   @Size(max=200)
/*  62:    */   private String descripcion;
/*  63:    */   @Column(name="indicador_planificada", nullable=false)
/*  64:    */   private boolean indicadorPlanificada;
/*  65:    */   @Column(name="estado", nullable=false)
/*  66:    */   @Enumerated(EnumType.ORDINAL)
/*  67:    */   private Estado estado;
/*  68:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenTrabajoMantenimiento")
/*  69: 93 */   private List<ResponsableOrdenTrabajoMantenimiento> listaResponsableOrdenTrabajoMantenimiento = new ArrayList();
/*  70:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenTrabajoMantenimiento")
/*  71: 96 */   private List<DetalleOrdenTrabajoMantenimiento> listaDetalleOrdenTrabajoMantenimiento = new ArrayList();
/*  72:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenTrabajoMantenimiento")
/*  73: 99 */   private List<MaterialOrdenTrabajoMantenimiento> listaMaterialOrdenTrabajoMantenimiento = new ArrayList();
/*  74:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ordenTrabajoMantenimiento")
/*  75:102 */   private List<HerramientaOrdenTrabajoMantenimiento> listaHerramientaOrdenTrabajoMantenimiento = new ArrayList();
/*  76:    */   @Column(name="indicador_agil", nullable=false)
/*  77:    */   @NotNull
/*  78:    */   @ColumnDefault("'0'")
/*  79:    */   private boolean indicadorAgil;
/*  80:    */   
/*  81:    */   public int getId()
/*  82:    */   {
/*  83:121 */     return this.idOrdenTrabajoMantenimiento;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdOrdenTrabajoMantenimiento()
/*  87:    */   {
/*  88:125 */     return this.idOrdenTrabajoMantenimiento;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdOrdenTrabajoMantenimiento(int idOrdenTrabajoMantenimiento)
/*  92:    */   {
/*  93:129 */     this.idOrdenTrabajoMantenimiento = idOrdenTrabajoMantenimiento;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdOrganizacion()
/*  97:    */   {
/*  98:133 */     return this.idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdOrganizacion(int idOrganizacion)
/* 102:    */   {
/* 103:137 */     this.idOrganizacion = idOrganizacion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getIdSucursal()
/* 107:    */   {
/* 108:141 */     return this.idSucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdSucursal(int idSucursal)
/* 112:    */   {
/* 113:145 */     this.idSucursal = idSucursal;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Documento getDocumento()
/* 117:    */   {
/* 118:149 */     return this.documento;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setDocumento(Documento documento)
/* 122:    */   {
/* 123:153 */     this.documento = documento;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getNumero()
/* 127:    */   {
/* 128:157 */     return this.numero;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setNumero(String numero)
/* 132:    */   {
/* 133:161 */     this.numero = numero;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Date getFechaMantenimiento()
/* 137:    */   {
/* 138:165 */     return this.fechaMantenimiento;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setFechaMantenimiento(Date fechaMantenimiento)
/* 142:    */   {
/* 143:169 */     this.fechaMantenimiento = fechaMantenimiento;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Date getFechaCierre()
/* 147:    */   {
/* 148:173 */     return this.fechaCierre;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setFechaCierre(Date fechaCierre)
/* 152:    */   {
/* 153:177 */     this.fechaCierre = fechaCierre;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String getDescripcion()
/* 157:    */   {
/* 158:181 */     return this.descripcion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setDescripcion(String descripcion)
/* 162:    */   {
/* 163:185 */     this.descripcion = descripcion;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean isIndicadorPlanificada()
/* 167:    */   {
/* 168:189 */     return this.indicadorPlanificada;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setIndicadorPlanificada(boolean indicadorPlanificada)
/* 172:    */   {
/* 173:193 */     this.indicadorPlanificada = indicadorPlanificada;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Estado getEstado()
/* 177:    */   {
/* 178:197 */     return this.estado;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setEstado(Estado estado)
/* 182:    */   {
/* 183:201 */     this.estado = estado;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<DetalleOrdenTrabajoMantenimiento> getListaDetalleOrdenTrabajoMantenimiento()
/* 187:    */   {
/* 188:205 */     return this.listaDetalleOrdenTrabajoMantenimiento;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setListaDetalleOrdenTrabajoMantenimiento(List<DetalleOrdenTrabajoMantenimiento> listaDetalleOrdenTrabajoMantenimiento)
/* 192:    */   {
/* 193:209 */     this.listaDetalleOrdenTrabajoMantenimiento = listaDetalleOrdenTrabajoMantenimiento;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public List<ResponsableOrdenTrabajoMantenimiento> getListaResponsableOrdenTrabajoMantenimiento()
/* 197:    */   {
/* 198:213 */     return this.listaResponsableOrdenTrabajoMantenimiento;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setListaResponsableOrdenTrabajoMantenimiento(List<ResponsableOrdenTrabajoMantenimiento> listaResponsableOrdenTrabajoMantenimiento)
/* 202:    */   {
/* 203:217 */     this.listaResponsableOrdenTrabajoMantenimiento = listaResponsableOrdenTrabajoMantenimiento;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public List<MaterialOrdenTrabajoMantenimiento> getListaMaterialOrdenTrabajoMantenimiento()
/* 207:    */   {
/* 208:221 */     return this.listaMaterialOrdenTrabajoMantenimiento;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setListaMaterialOrdenTrabajoMantenimiento(List<MaterialOrdenTrabajoMantenimiento> listaMaterialOrdenTrabajoMantenimiento)
/* 212:    */   {
/* 213:225 */     this.listaMaterialOrdenTrabajoMantenimiento = listaMaterialOrdenTrabajoMantenimiento;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public List<HerramientaOrdenTrabajoMantenimiento> getListaHerramientaOrdenTrabajoMantenimiento()
/* 217:    */   {
/* 218:229 */     return this.listaHerramientaOrdenTrabajoMantenimiento;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setListaHerramientaOrdenTrabajoMantenimiento(List<HerramientaOrdenTrabajoMantenimiento> listaHerramientaOrdenTrabajoMantenimiento)
/* 222:    */   {
/* 223:233 */     this.listaHerramientaOrdenTrabajoMantenimiento = listaHerramientaOrdenTrabajoMantenimiento;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean isIndicadorAgil()
/* 227:    */   {
/* 228:237 */     return this.indicadorAgil;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setIndicadorAgil(boolean indicadorAgil)
/* 232:    */   {
/* 233:241 */     this.indicadorAgil = indicadorAgil;
/* 234:    */   }
/* 235:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.OrdenTrabajoMantenimiento
 * JD-Core Version:    0.7.0.1
 */