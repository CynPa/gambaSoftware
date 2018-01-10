/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
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
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.Max;
/*  18:    */ import javax.validation.constraints.Min;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="unidad", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"codigo", "id_organizacion"})})
/*  24:    */ public class Unidad
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="unidad", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="unidad")
/*  31:    */   @Column(name="id_unidad", unique=true, nullable=false)
/*  32:    */   private int idUnidad;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="codigo", nullable=false, length=10)
/*  38:    */   @NotNull
/*  39:    */   @Size(max=10)
/*  40:    */   private String codigo;
/*  41:    */   @Column(name="nombre", nullable=false, length=50)
/*  42:    */   @NotNull
/*  43:    */   @Size(max=50)
/*  44:    */   private String nombre;
/*  45:    */   @Column(name="numero_decimales", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   @Min(0L)
/*  48:    */   @Max(6L)
/*  49: 77 */   private Integer numeroDecimales = Integer.valueOf(2);
/*  50:    */   @Column(name="descripcion", length=200)
/*  51:    */   @Size(max=200)
/*  52:    */   private String descripcion;
/*  53:    */   @Column(name="tipo_unidad_medida", nullable=false)
/*  54:    */   @Enumerated(EnumType.ORDINAL)
/*  55:    */   private TipoUnidadMedida tipoUnidadMedida;
/*  56:    */   @Column(name="activo", nullable=false)
/*  57:    */   private boolean activo;
/*  58:    */   @Column(name="predeterminado", nullable=false)
/*  59:    */   private boolean predeterminado;
/*  60:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="unidadOrigen")
/*  61: 93 */   private List<UnidadConversion> listaUnidadConversion = new ArrayList();
/*  62:    */   
/*  63:    */   public Unidad() {}
/*  64:    */   
/*  65:    */   public Unidad(int idUnidad, String codigo, String nombre)
/*  66:    */   {
/*  67:112 */     this.idUnidad = idUnidad;
/*  68:113 */     this.codigo = codigo;
/*  69:114 */     this.nombre = nombre;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getId()
/*  73:    */   {
/*  74:124 */     return this.idUnidad;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdUnidad()
/*  78:    */   {
/*  79:128 */     return this.idUnidad;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdUnidad(int idUnidad)
/*  83:    */   {
/*  84:132 */     this.idUnidad = idUnidad;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdOrganizacion()
/*  88:    */   {
/*  89:136 */     return this.idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdOrganizacion(int idOrganizacion)
/*  93:    */   {
/*  94:140 */     this.idOrganizacion = idOrganizacion;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getIdSucursal()
/*  98:    */   {
/*  99:144 */     return this.idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setIdSucursal(Integer idSucursal)
/* 103:    */   {
/* 104:148 */     this.idSucursal = idSucursal.intValue();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getCodigo()
/* 108:    */   {
/* 109:152 */     return this.codigo;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setCodigo(String codigo)
/* 113:    */   {
/* 114:156 */     this.codigo = codigo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String getNombre()
/* 118:    */   {
/* 119:160 */     return this.nombre;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setNombre(String nombre)
/* 123:    */   {
/* 124:164 */     this.nombre = nombre;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getDescripcion()
/* 128:    */   {
/* 129:168 */     return this.descripcion;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setDescripcion(String descripcion)
/* 133:    */   {
/* 134:172 */     this.descripcion = descripcion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean getActivo()
/* 138:    */   {
/* 139:176 */     return this.activo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean getPredeterminado()
/* 143:    */   {
/* 144:180 */     return this.predeterminado;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isActivo()
/* 148:    */   {
/* 149:189 */     return this.activo;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setActivo(boolean activo)
/* 153:    */   {
/* 154:199 */     this.activo = activo;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isPredeterminado()
/* 158:    */   {
/* 159:208 */     return this.predeterminado;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setPredeterminado(boolean predeterminado)
/* 163:    */   {
/* 164:218 */     this.predeterminado = predeterminado;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String toString()
/* 168:    */   {
/* 169:228 */     return this.nombre;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List<UnidadConversion> getListaUnidadConversion()
/* 173:    */   {
/* 174:237 */     return this.listaUnidadConversion;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setListaUnidadConversion(List<UnidadConversion> listaUnidadConversion)
/* 178:    */   {
/* 179:247 */     this.listaUnidadConversion = listaUnidadConversion;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public TipoUnidadMedida getTipoUnidadMedida()
/* 183:    */   {
/* 184:256 */     return this.tipoUnidadMedida;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setTipoUnidadMedida(TipoUnidadMedida tipoUnidadMedida)
/* 188:    */   {
/* 189:266 */     this.tipoUnidadMedida = tipoUnidadMedida;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Integer getNumeroDecimales()
/* 193:    */   {
/* 194:270 */     return this.numeroDecimales;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setNumeroDecimales(Integer numeroDecimales)
/* 198:    */   {
/* 199:274 */     this.numeroDecimales = numeroDecimales;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setIdSucursal(int idSucursal)
/* 203:    */   {
/* 204:278 */     this.idSucursal = idSucursal;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public List<String> getCamposAuditables()
/* 208:    */   {
/* 209:288 */     ArrayList<String> lista = new ArrayList();
/* 210:289 */     lista.add("codigo");
/* 211:290 */     lista.add("nombre");
/* 212:291 */     lista.add("descripcion");
/* 213:292 */     lista.add("activo");
/* 214:293 */     lista.add("predeterminado");
/* 215:294 */     lista.add("numeroDecimales");
/* 216:    */     
/* 217:296 */     return lista;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public String getMascara()
/* 221:    */   {
/* 222:300 */     String mascara = ",###,##0.00";
/* 223:    */     
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:305 */     return mascara;
/* 228:    */   }
/* 229:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Unidad
 * JD-Core Version:    0.7.0.1
 */