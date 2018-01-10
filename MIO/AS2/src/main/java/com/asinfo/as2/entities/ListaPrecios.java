/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoListaPreciosEnum;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.OneToMany;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ import org.hibernate.annotations.ColumnDefault;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="lista_precios", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"codigo", "id_organizacion"})})
/*  25:    */ public class ListaPrecios
/*  26:    */   extends EntidadBase
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="lista_precios", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="lista_precios")
/*  32:    */   @Column(name="id_lista_precios")
/*  33:    */   private int idListaPrecios;
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
/*  55:    */   @Column(name="indicador_venta", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private boolean indicadorVenta;
/*  58:    */   @Column(name="indicador_compra", nullable=false)
/*  59:    */   @NotNull
/*  60:    */   private boolean indicadorCompra;
/*  61:    */   @Column(name="indicador_impresion_etiqueta", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   @ColumnDefault("'0'")
/*  64:    */   private boolean indicadorImpresionEtiqueta;
/*  65:    */   @Enumerated(EnumType.ORDINAL)
/*  66:    */   @Column(name="tipo_lista_precios", nullable=false)
/*  67: 96 */   private TipoListaPreciosEnum tipoListaPrecios = TipoListaPreciosEnum.LISTA_PRECIOS;
/*  68:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  69:    */   @JoinColumn(name="id_moneda")
/*  70:    */   private Moneda moneda;
/*  71:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="listaPrecios")
/*  72:104 */   private List<VersionListaPrecios> versionesListaPrecios = new ArrayList();
/*  73:    */   
/*  74:    */   public ListaPrecios() {}
/*  75:    */   
/*  76:    */   public ListaPrecios(int idListaPrecios, String codigo, String nombre)
/*  77:    */   {
/*  78:114 */     this.idListaPrecios = idListaPrecios;
/*  79:115 */     this.codigo = codigo;
/*  80:116 */     this.nombre = nombre;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getId()
/*  84:    */   {
/*  85:121 */     return this.idListaPrecios;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdListaPrecios()
/*  89:    */   {
/*  90:130 */     return this.idListaPrecios;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdListaPrecios(int idListaPrecios)
/*  94:    */   {
/*  95:140 */     this.idListaPrecios = idListaPrecios;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getIdOrganizacion()
/*  99:    */   {
/* 100:149 */     return this.idOrganizacion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIdOrganizacion(int idOrganizacion)
/* 104:    */   {
/* 105:159 */     this.idOrganizacion = idOrganizacion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getIdSucursal()
/* 109:    */   {
/* 110:168 */     return this.idSucursal;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIdSucursal(int idSucursal)
/* 114:    */   {
/* 115:178 */     this.idSucursal = idSucursal;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getCodigo()
/* 119:    */   {
/* 120:187 */     return this.codigo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setCodigo(String codigo)
/* 124:    */   {
/* 125:197 */     this.codigo = codigo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getNombre()
/* 129:    */   {
/* 130:206 */     return this.nombre;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setNombre(String nombre)
/* 134:    */   {
/* 135:216 */     this.nombre = nombre;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getDescripcion()
/* 139:    */   {
/* 140:225 */     return this.descripcion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setDescripcion(String descripcion)
/* 144:    */   {
/* 145:235 */     this.descripcion = descripcion;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isActivo()
/* 149:    */   {
/* 150:244 */     return this.activo;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setActivo(boolean activo)
/* 154:    */   {
/* 155:254 */     this.activo = activo;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean isPredeterminado()
/* 159:    */   {
/* 160:263 */     return this.predeterminado;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setPredeterminado(boolean predeterminado)
/* 164:    */   {
/* 165:273 */     this.predeterminado = predeterminado;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean isIndicadorVenta()
/* 169:    */   {
/* 170:282 */     return this.indicadorVenta;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIndicadorVenta(boolean indicadorVenta)
/* 174:    */   {
/* 175:292 */     this.indicadorVenta = indicadorVenta;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isIndicadorCompra()
/* 179:    */   {
/* 180:301 */     return this.indicadorCompra;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setIndicadorCompra(boolean indicadorCompra)
/* 184:    */   {
/* 185:311 */     this.indicadorCompra = indicadorCompra;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Moneda getMoneda()
/* 189:    */   {
/* 190:320 */     return this.moneda;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setMoneda(Moneda moneda)
/* 194:    */   {
/* 195:330 */     this.moneda = moneda;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public static long getSerialversionuid()
/* 199:    */   {
/* 200:339 */     return 1L;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public List<VersionListaPrecios> getVersionesListaPrecios()
/* 204:    */   {
/* 205:348 */     return this.versionesListaPrecios;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setVersionesListaPrecios(List<VersionListaPrecios> versionesListaPrecios)
/* 209:    */   {
/* 210:358 */     this.versionesListaPrecios = versionesListaPrecios;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public String toString()
/* 214:    */   {
/* 215:363 */     return this.nombre;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public TipoListaPreciosEnum getTipoListaPrecios()
/* 219:    */   {
/* 220:370 */     return this.tipoListaPrecios;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setTipoListaPrecios(TipoListaPreciosEnum tipoListaPrecios)
/* 224:    */   {
/* 225:378 */     this.tipoListaPrecios = tipoListaPrecios;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean isIndicadorImpresionEtiqueta()
/* 229:    */   {
/* 230:382 */     return this.indicadorImpresionEtiqueta;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setIndicadorImpresionEtiqueta(boolean indicadorImpresionEtiqueta)
/* 234:    */   {
/* 235:386 */     this.indicadorImpresionEtiqueta = indicadorImpresionEtiqueta;
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ListaPrecios
 * JD-Core Version:    0.7.0.1
 */