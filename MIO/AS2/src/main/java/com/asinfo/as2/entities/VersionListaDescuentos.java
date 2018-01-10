/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Temporal;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.Max;
/*  22:    */ import javax.validation.constraints.Min;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ import org.hibernate.annotations.ColumnDefault;
/*  26:    */ 
/*  27:    */ @Entity
/*  28:    */ @Table(name="version_lista_descuentos")
/*  29:    */ public class VersionListaDescuentos
/*  30:    */   extends EntidadBase
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -2403452113924604488L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="version_lista_descuentos", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="version_lista_descuentos")
/*  36:    */   @Column(name="id_version_lista_descuentos")
/*  37:    */   private int idVersionListaDescuentos;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="id_sucursal", nullable=false)
/*  41:    */   private int idSucursal;
/*  42:    */   @Column(name="descripcion", length=200)
/*  43:    */   @Size(max=200)
/*  44:    */   private String descripcion;
/*  45:    */   @Column(name="activo", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private boolean activo;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   private boolean predeterminado;
/*  51:    */   @Temporal(TemporalType.DATE)
/*  52:    */   @Column(name="valido_desde", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   protected Date validoDesde;
/*  55:    */   @Temporal(TemporalType.DATE)
/*  56:    */   @Column(name="valido_hasta", nullable=true)
/*  57:    */   protected Date validoHasta;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_lista_descuentos")
/*  60:    */   private ListaDescuentos listaDescuentos;
/*  61:    */   @Column(name="porcentaje_descuento_maximo", nullable=false, precision=5, scale=2)
/*  62:    */   @Min(0L)
/*  63:    */   @Max(100L)
/*  64:    */   @ColumnDefault("0")
/*  65: 91 */   private BigDecimal porcentajeDescuentoMaximo = BigDecimal.ZERO;
/*  66:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="versionListaDescuentos")
/*  67: 97 */   private List<DetalleListaDescuentos> listaDetalleListaDescuentos = new ArrayList();
/*  68:    */   @Transient
/*  69:    */   private String traListaDescuentos;
/*  70:    */   
/*  71:    */   public VersionListaDescuentos() {}
/*  72:    */   
/*  73:    */   public VersionListaDescuentos(int idVersionListaDescuentos, String codigo, String nombre, String descripcion, boolean activo, boolean predeterminado, Date validoDesde, Date validoHasta, String traListaDescuentos)
/*  74:    */   {
/*  75:109 */     this.idVersionListaDescuentos = idVersionListaDescuentos;
/*  76:110 */     this.descripcion = descripcion;
/*  77:111 */     this.activo = activo;
/*  78:112 */     this.predeterminado = predeterminado;
/*  79:113 */     this.validoDesde = validoDesde;
/*  80:114 */     this.validoHasta = validoHasta;
/*  81:115 */     this.traListaDescuentos = traListaDescuentos;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getId()
/*  85:    */   {
/*  86:120 */     return this.idVersionListaDescuentos;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdVersionListaDescuentos()
/*  90:    */   {
/*  91:129 */     return this.idVersionListaDescuentos;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdVersionListaDescuentos(int idVersionListaDescuentos)
/*  95:    */   {
/*  96:139 */     this.idVersionListaDescuentos = idVersionListaDescuentos;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdOrganizacion()
/* 100:    */   {
/* 101:148 */     return this.idOrganizacion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdOrganizacion(int idOrganizacion)
/* 105:    */   {
/* 106:158 */     this.idOrganizacion = idOrganizacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getIdSucursal()
/* 110:    */   {
/* 111:167 */     return this.idSucursal;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setIdSucursal(int idSucursal)
/* 115:    */   {
/* 116:177 */     this.idSucursal = idSucursal;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getDescripcion()
/* 120:    */   {
/* 121:186 */     return this.descripcion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setDescripcion(String descripcion)
/* 125:    */   {
/* 126:196 */     this.descripcion = descripcion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean isActivo()
/* 130:    */   {
/* 131:205 */     return this.activo;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setActivo(boolean activo)
/* 135:    */   {
/* 136:215 */     this.activo = activo;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isPredeterminado()
/* 140:    */   {
/* 141:224 */     return this.predeterminado;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setPredeterminado(boolean predeterminado)
/* 145:    */   {
/* 146:234 */     this.predeterminado = predeterminado;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Date getValidoDesde()
/* 150:    */   {
/* 151:243 */     return this.validoDesde;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setValidoDesde(Date validoDesde)
/* 155:    */   {
/* 156:253 */     this.validoDesde = validoDesde;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Date getValidoHasta()
/* 160:    */   {
/* 161:257 */     return this.validoHasta;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setValidoHasta(Date validoHasta)
/* 165:    */   {
/* 166:261 */     this.validoHasta = validoHasta;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public ListaDescuentos getListaDescuentos()
/* 170:    */   {
/* 171:270 */     return this.listaDescuentos;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setListaDescuentos(ListaDescuentos listaDescuentos)
/* 175:    */   {
/* 176:280 */     this.listaDescuentos = listaDescuentos;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public List<DetalleListaDescuentos> getListaDetalleListaDescuentos()
/* 180:    */   {
/* 181:284 */     return this.listaDetalleListaDescuentos;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setListaDetalleListaDescuentos(List<DetalleListaDescuentos> listaDetalleListaDescuentos)
/* 185:    */   {
/* 186:288 */     this.listaDetalleListaDescuentos = listaDetalleListaDescuentos;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getTraListaDescuentos()
/* 190:    */   {
/* 191:292 */     return this.traListaDescuentos;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setTraListaDescuentos(String traListaDescuentos)
/* 195:    */   {
/* 196:296 */     this.traListaDescuentos = traListaDescuentos;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public BigDecimal getPorcentajeDescuentoMaximo()
/* 200:    */   {
/* 201:300 */     return this.porcentajeDescuentoMaximo;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setPorcentajeDescuentoMaximo(BigDecimal porcentajeDescuentoMaximo)
/* 205:    */   {
/* 206:304 */     this.porcentajeDescuentoMaximo = porcentajeDescuentoMaximo;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setEliminado(boolean eliminado)
/* 210:    */   {
/* 211:309 */     for (DetalleListaDescuentos detalleListaDescuentos : this.listaDetalleListaDescuentos) {
/* 212:310 */       detalleListaDescuentos.setEliminado(true);
/* 213:    */     }
/* 214:312 */     super.setEliminado(eliminado);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String toString()
/* 218:    */   {
/* 219:317 */     return this.descripcion;
/* 220:    */   }
/* 221:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.VersionListaDescuentos
 * JD-Core Version:    0.7.0.1
 */