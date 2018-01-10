/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Temporal;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="version_lista_precios")
/*  25:    */ public class VersionListaPrecios
/*  26:    */   extends EntidadBase
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -2403452113924604488L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="version_lista_precios", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="version_lista_precios")
/*  32:    */   @Column(name="id_version_lista_precios")
/*  33:    */   private int idVersionListaPrecios;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="codigo", length=10, nullable=false)
/*  39:    */   @NotNull
/*  40:    */   @Size(max=10)
/*  41:    */   private String codigo;
/*  42:    */   @Column(name="nombre", length=50, nullable=false)
/*  43:    */   @NotNull
/*  44:    */   @Size(max=50)
/*  45:    */   private String nombre;
/*  46:    */   @Column(name="descripcion", length=200)
/*  47:    */   @Size(max=200)
/*  48:    */   private String descripcion;
/*  49:    */   @Column(name="activo", nullable=false)
/*  50:    */   @NotNull
/*  51:    */   private boolean activo;
/*  52:    */   @Column(name="predeterminado", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   private boolean predeterminado;
/*  55:    */   @Temporal(TemporalType.DATE)
/*  56:    */   @Column(name="valido_desde", nullable=false)
/*  57:    */   @NotNull
/*  58:    */   protected Date validoDesde;
/*  59:    */   @Temporal(TemporalType.DATE)
/*  60:    */   @Column(name="valido_hasta", nullable=true)
/*  61:    */   protected Date validoHasta;
/*  62:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  63:    */   @JoinColumn(name="id_lista_precios")
/*  64:    */   private ListaPrecios listaPrecios;
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="id_zona", nullable=true)
/*  67:    */   private Zona zona;
/*  68:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="versionListaPrecios")
/*  69:100 */   private List<DetalleVersionListaPrecios> detalleVersionesListaPrecios = new ArrayList();
/*  70:    */   @Transient
/*  71:    */   private String traListaPrecios;
/*  72:    */   
/*  73:    */   public VersionListaPrecios() {}
/*  74:    */   
/*  75:    */   public VersionListaPrecios(int idVersionListaPrecios, String codigo, String nombre, String descripcion, boolean activo, boolean predeterminado, Date validoDesde, Date validoHasta, String traListaPrecios)
/*  76:    */   {
/*  77:112 */     this.idVersionListaPrecios = idVersionListaPrecios;
/*  78:113 */     this.codigo = codigo;
/*  79:114 */     this.nombre = nombre;
/*  80:115 */     this.descripcion = descripcion;
/*  81:116 */     this.activo = activo;
/*  82:117 */     this.predeterminado = predeterminado;
/*  83:118 */     this.validoDesde = validoDesde;
/*  84:119 */     this.validoHasta = validoHasta;
/*  85:120 */     this.traListaPrecios = traListaPrecios;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getId()
/*  89:    */   {
/*  90:125 */     return this.idVersionListaPrecios;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getIdVersionListaPrecios()
/*  94:    */   {
/*  95:134 */     return this.idVersionListaPrecios;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdVersionListaPrecios(int idVersionListaPrecios)
/*  99:    */   {
/* 100:144 */     this.idVersionListaPrecios = idVersionListaPrecios;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getIdOrganizacion()
/* 104:    */   {
/* 105:153 */     return this.idOrganizacion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setIdOrganizacion(int idOrganizacion)
/* 109:    */   {
/* 110:163 */     this.idOrganizacion = idOrganizacion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getIdSucursal()
/* 114:    */   {
/* 115:172 */     return this.idSucursal;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setIdSucursal(int idSucursal)
/* 119:    */   {
/* 120:182 */     this.idSucursal = idSucursal;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getCodigo()
/* 124:    */   {
/* 125:191 */     return this.codigo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setCodigo(String codigo)
/* 129:    */   {
/* 130:201 */     this.codigo = codigo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String getNombre()
/* 134:    */   {
/* 135:210 */     return this.nombre;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setNombre(String nombre)
/* 139:    */   {
/* 140:220 */     this.nombre = nombre;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String getDescripcion()
/* 144:    */   {
/* 145:229 */     return this.descripcion;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setDescripcion(String descripcion)
/* 149:    */   {
/* 150:239 */     this.descripcion = descripcion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean isActivo()
/* 154:    */   {
/* 155:248 */     return this.activo;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setActivo(boolean activo)
/* 159:    */   {
/* 160:258 */     this.activo = activo;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean isPredeterminado()
/* 164:    */   {
/* 165:267 */     return this.predeterminado;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setPredeterminado(boolean predeterminado)
/* 169:    */   {
/* 170:277 */     this.predeterminado = predeterminado;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Date getValidoDesde()
/* 174:    */   {
/* 175:286 */     return this.validoDesde;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setValidoDesde(Date validoDesde)
/* 179:    */   {
/* 180:296 */     this.validoDesde = validoDesde;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Date getValidoHasta()
/* 184:    */   {
/* 185:300 */     return this.validoHasta;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setValidoHasta(Date validoHasta)
/* 189:    */   {
/* 190:304 */     this.validoHasta = validoHasta;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public ListaPrecios getListaPrecios()
/* 194:    */   {
/* 195:313 */     return this.listaPrecios;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setListaPrecios(ListaPrecios listaPrecios)
/* 199:    */   {
/* 200:323 */     this.listaPrecios = listaPrecios;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public List<DetalleVersionListaPrecios> getDetalleVersionesListaPrecios()
/* 204:    */   {
/* 205:332 */     return this.detalleVersionesListaPrecios;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setDetalleVersionesListaPrecios(List<DetalleVersionListaPrecios> detalleVersionesListaPrecios)
/* 209:    */   {
/* 210:342 */     this.detalleVersionesListaPrecios = detalleVersionesListaPrecios;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public String getTraListaPrecios()
/* 214:    */   {
/* 215:346 */     return this.traListaPrecios;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setTraListaPrecios(String traListaPrecios)
/* 219:    */   {
/* 220:350 */     this.traListaPrecios = traListaPrecios;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Zona getZona()
/* 224:    */   {
/* 225:359 */     return this.zona;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setZona(Zona zona)
/* 229:    */   {
/* 230:369 */     this.zona = zona;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String toString()
/* 234:    */   {
/* 235:374 */     return this.nombre;
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.VersionListaPrecios
 * JD-Core Version:    0.7.0.1
 */