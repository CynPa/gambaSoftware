/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.OneToMany;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.DecimalMax;
/*  17:    */ import javax.validation.constraints.DecimalMin;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ import org.hibernate.annotations.ColumnDefault;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="pais", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo_ISO"})})
/*  24:    */ public class Pais
/*  25:    */   extends EntidadBase
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="pais", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="pais")
/*  32:    */   @Column(name="id_pais", unique=true, nullable=false)
/*  33:    */   private int idPais;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="nombre", nullable=false, length=50)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=2, max=50)
/*  41:    */   private String nombre;
/*  42:    */   @Column(name="codigo_ISO", nullable=false, length=10)
/*  43:    */   @NotNull
/*  44:    */   @Size(min=2, max=10)
/*  45:    */   private String codigoIso;
/*  46:    */   @Column(name="codigo", nullable=false, length=10)
/*  47:    */   @NotNull
/*  48:    */   @Size(min=2, max=10)
/*  49:    */   @ColumnDefault("''")
/*  50:    */   private String codigo;
/*  51:    */   @Column(name="gentilicio", nullable=true, length=100)
/*  52:    */   private String gentilicio;
/*  53:    */   @Column(name="descripcion", nullable=true, length=200)
/*  54:    */   @Size(max=200)
/*  55:    */   private String descripcion;
/*  56:    */   @Column(name="activo", nullable=false)
/*  57:    */   private boolean activo;
/*  58:    */   @Column(name="predeterminado", nullable=false)
/*  59:    */   private boolean predeterminado;
/*  60:    */   @ColumnDefault("0")
/*  61:    */   @DecimalMax("100.00")
/*  62:    */   @DecimalMin("0.00")
/*  63:    */   @Column(name="porcentaje_descuento_ATPDEA", nullable=false, precision=3, scale=2)
/*  64: 86 */   private BigDecimal porcentajeDescuentoATPDEA = BigDecimal.ZERO;
/*  65:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="pais")
/*  66: 92 */   private List<Provincia> listaProvincia = new ArrayList();
/*  67:    */   
/*  68:    */   public int getId()
/*  69:    */   {
/*  70:102 */     return this.idPais;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Pais() {}
/*  74:    */   
/*  75:    */   public Pais(int idPais, String nombre)
/*  76:    */   {
/*  77:114 */     this.idPais = idPais;
/*  78:115 */     this.nombre = nombre;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdPais()
/*  82:    */   {
/*  83:124 */     return this.idPais;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdPais(int idPais)
/*  87:    */   {
/*  88:134 */     this.idPais = idPais;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getIdOrganizacion()
/*  92:    */   {
/*  93:143 */     return this.idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdOrganizacion(int idOrganizacion)
/*  97:    */   {
/*  98:153 */     this.idOrganizacion = idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public int getIdSucursal()
/* 102:    */   {
/* 103:162 */     return this.idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIdSucursal(int idSucursal)
/* 107:    */   {
/* 108:172 */     this.idSucursal = idSucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getNombre()
/* 112:    */   {
/* 113:181 */     return this.nombre;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setNombre(String nombre)
/* 117:    */   {
/* 118:191 */     this.nombre = nombre;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String getCodigoIso()
/* 122:    */   {
/* 123:200 */     return this.codigoIso;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setCodigoIso(String codigoIso)
/* 127:    */   {
/* 128:210 */     this.codigoIso = codigoIso;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String getDescripcion()
/* 132:    */   {
/* 133:219 */     return this.descripcion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setDescripcion(String descripcion)
/* 137:    */   {
/* 138:229 */     this.descripcion = descripcion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean isActivo()
/* 142:    */   {
/* 143:238 */     return this.activo;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setActivo(boolean activo)
/* 147:    */   {
/* 148:248 */     this.activo = activo;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isPredeterminado()
/* 152:    */   {
/* 153:257 */     return this.predeterminado;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setPredeterminado(boolean predeterminado)
/* 157:    */   {
/* 158:267 */     this.predeterminado = predeterminado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public List<Provincia> getListaProvincia()
/* 162:    */   {
/* 163:276 */     return this.listaProvincia;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setListaProvincia(List<Provincia> listaProvincia)
/* 167:    */   {
/* 168:286 */     this.listaProvincia = listaProvincia;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String getGentilicio()
/* 172:    */   {
/* 173:295 */     return this.gentilicio;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setGentilicio(String gentilicio)
/* 177:    */   {
/* 178:305 */     this.gentilicio = gentilicio;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<String> getCamposAuditables()
/* 182:    */   {
/* 183:309 */     List<String> lista = new ArrayList();
/* 184:310 */     lista.add("nombre");
/* 185:311 */     lista.add("codigoIso");
/* 186:312 */     lista.add("activo");
/* 187:313 */     lista.add("predeterminado");
/* 188:314 */     return lista;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String toString()
/* 192:    */   {
/* 193:319 */     return this.nombre;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String getCodigo()
/* 197:    */   {
/* 198:323 */     return this.codigo;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setCodigo(String codigo)
/* 202:    */   {
/* 203:327 */     this.codigo = codigo;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public BigDecimal getPorcentajeDescuentoATPDEA()
/* 207:    */   {
/* 208:331 */     return this.porcentajeDescuentoATPDEA;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setPorcentajeDescuentoATPDEA(BigDecimal porcentajeDescuentoATPDEA)
/* 212:    */   {
/* 213:335 */     this.porcentajeDescuentoATPDEA = porcentajeDescuentoATPDEA;
/* 214:    */   }
/* 215:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Pais
 * JD-Core Version:    0.7.0.1
 */