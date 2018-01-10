/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.ComprobanteSRICreditoTributarioSRI;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.entities.TipoIdentificacionComprobanteSRI;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Temporal;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="tipo_comprobanteSRI")
/*  26:    */ public class TipoComprobanteSRI
/*  27:    */   extends EntidadBase
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="tipo_comprobanteSRI", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_comprobanteSRI")
/*  33:    */   @Column(name="id_tipo_comprobanteSRI")
/*  34:    */   private int idTipoComprobanteSRI;
/*  35:    */   @Column(name="id_organizacion")
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal")
/*  38:    */   private int idSucursal;
/*  39:    */   @Column(name="codigo", length=10, nullable=false)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=1, max=10)
/*  42:    */   private String codigo;
/*  43:    */   @Column(name="nombre", length=200, nullable=false)
/*  44:    */   @NotNull
/*  45:    */   @Size(min=2, max=200)
/*  46:    */   private String nombre;
/*  47:    */   @Column(name="descripcion", length=200)
/*  48:    */   @Size(max=200)
/*  49:    */   private String descripcion;
/*  50:    */   @Column(name="activo", nullable=false)
/*  51:    */   private boolean activo;
/*  52:    */   @Column(name="predeterminado", nullable=false)
/*  53:    */   private boolean predeterminado;
/*  54:    */   @Temporal(TemporalType.DATE)
/*  55:    */   @Column(name="fecha_desde", nullable=false, length=23)
/*  56:    */   @NotNull
/*  57:    */   private Date fechaDesde;
/*  58:    */   @Temporal(TemporalType.DATE)
/*  59:    */   @Column(name="fecha_hasta", nullable=false, length=23)
/*  60:    */   @NotNull
/*  61:    */   private Date fechaHasta;
/*  62:    */   @Column(name="codigo_documento_electronico", length=200)
/*  63:    */   @Size(max=10)
/*  64:    */   private String codigoDocumentoElectronico;
/*  65:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="tipoComprobanteSRI")
/*  66: 94 */   private List<TipoIdentificacionComprobanteSRI> listaTipoIdentificacionComprobanteSRI = new ArrayList();
/*  67:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="tipoComprobanteSRI")
/*  68: 97 */   private List<ComprobanteSRICreditoTributarioSRI> listaComprobanteSRICreditoTributarioSRI = new ArrayList();
/*  69:    */   @Transient
/*  70:100 */   private List<CreditoTributarioSRI> listaCreditoTributarioSRI = new ArrayList();
/*  71:    */   
/*  72:    */   public int getIdTipoComprobanteSRI()
/*  73:    */   {
/*  74:104 */     return this.idTipoComprobanteSRI;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdTipoComprobanteSRI(int idTipoComprobanteSRI)
/*  78:    */   {
/*  79:108 */     this.idTipoComprobanteSRI = idTipoComprobanteSRI;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdOrganizacion()
/*  83:    */   {
/*  84:112 */     return this.idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdOrganizacion(int idOrganizacion)
/*  88:    */   {
/*  89:116 */     this.idOrganizacion = idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdSucursal()
/*  93:    */   {
/*  94:120 */     return this.idSucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdSucursal(int idSucursal)
/*  98:    */   {
/*  99:124 */     this.idSucursal = idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getCodigo()
/* 103:    */   {
/* 104:128 */     return this.codigo;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setCodigo(String codigo)
/* 108:    */   {
/* 109:132 */     this.codigo = codigo;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getNombre()
/* 113:    */   {
/* 114:136 */     return this.nombre;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setNombre(String nombre)
/* 118:    */   {
/* 119:140 */     this.nombre = nombre;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getDescripcion()
/* 123:    */   {
/* 124:144 */     return this.descripcion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setDescripcion(String descripcion)
/* 128:    */   {
/* 129:148 */     this.descripcion = descripcion;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isActivo()
/* 133:    */   {
/* 134:152 */     return this.activo;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setActivo(boolean activo)
/* 138:    */   {
/* 139:156 */     this.activo = activo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean isPredeterminado()
/* 143:    */   {
/* 144:160 */     return this.predeterminado;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setPredeterminado(boolean predeterminado)
/* 148:    */   {
/* 149:164 */     this.predeterminado = predeterminado;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Date getFechaDesde()
/* 153:    */   {
/* 154:168 */     return this.fechaDesde;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setFechaDesde(Date fechaDesde)
/* 158:    */   {
/* 159:172 */     this.fechaDesde = fechaDesde;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Date getFechaHasta()
/* 163:    */   {
/* 164:176 */     return this.fechaHasta;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setFechaHasta(Date fechaHasta)
/* 168:    */   {
/* 169:180 */     this.fechaHasta = fechaHasta;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String getCodigoDocumentoElectronico()
/* 173:    */   {
/* 174:184 */     return this.codigoDocumentoElectronico;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setCodigoDocumentoElectronico(String codigoDocumentoElectronico)
/* 178:    */   {
/* 179:188 */     this.codigoDocumentoElectronico = codigoDocumentoElectronico;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public int getId()
/* 183:    */   {
/* 184:193 */     return getIdTipoComprobanteSRI();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String toString()
/* 188:    */   {
/* 189:198 */     return this.nombre;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<TipoIdentificacionComprobanteSRI> getListaTipoIdentificacionComprobanteSRI()
/* 193:    */   {
/* 194:205 */     return this.listaTipoIdentificacionComprobanteSRI;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setListaTipoIdentificacionComprobanteSRI(List<TipoIdentificacionComprobanteSRI> listaTipoIdentificacionComprobanteSRI)
/* 198:    */   {
/* 199:213 */     this.listaTipoIdentificacionComprobanteSRI = listaTipoIdentificacionComprobanteSRI;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<ComprobanteSRICreditoTributarioSRI> getListaComprobanteSRICreditoTributarioSRI()
/* 203:    */   {
/* 204:220 */     return this.listaComprobanteSRICreditoTributarioSRI;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setListaComprobanteSRICreditoTributarioSRI(List<ComprobanteSRICreditoTributarioSRI> listaComprobanteSRICreditoTributarioSRI)
/* 208:    */   {
/* 209:228 */     this.listaComprobanteSRICreditoTributarioSRI = listaComprobanteSRICreditoTributarioSRI;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<CreditoTributarioSRI> getListaCreditoTributarioSRI()
/* 213:    */   {
/* 214:235 */     return this.listaCreditoTributarioSRI;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setListaCreditoTributarioSRI(List<CreditoTributarioSRI> listaCreditoTributarioSRI)
/* 218:    */   {
/* 219:243 */     this.listaCreditoTributarioSRI = listaCreditoTributarioSRI;
/* 220:    */   }
/* 221:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.TipoComprobanteSRI
 * JD-Core Version:    0.7.0.1
 */