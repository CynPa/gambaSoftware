/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
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
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="ciudad", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_provincia", "codigo"})})
/*  22:    */ public class Ciudad
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 2977213840972094356L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="ciudad", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ciudad")
/*  30:    */   @Column(name="id_ciudad")
/*  31:    */   private int idCiudad;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_provincia", nullable=true)
/*  38:    */   private Provincia provincia;
/*  39:    */   @Column(name="codigo", length=10, nullable=false)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=2, max=10)
/*  42:    */   private String codigo;
/*  43:    */   @Column(name="nombre", length=50, nullable=false)
/*  44:    */   @NotNull
/*  45:    */   @Size(min=2, max=50)
/*  46:    */   private String nombre;
/*  47:    */   @Column(name="codigo_postal", length=10, nullable=false)
/*  48:    */   @NotNull
/*  49:    */   @Size(min=2, max=10)
/*  50:    */   private String codigoPostal;
/*  51:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ciudad")
/*  52: 75 */   private List<Parroquia> listaParroquia = new ArrayList();
/*  53:    */   @Column(name="descripcion", length=200, nullable=true)
/*  54:    */   @Size(max=200)
/*  55:    */   private String descripcion;
/*  56:    */   @Column(name="activo", nullable=false)
/*  57:    */   private boolean activo;
/*  58:    */   @Column(name="predeterminado", nullable=false)
/*  59:    */   private boolean predeterminado;
/*  60:    */   
/*  61:    */   public Ciudad() {}
/*  62:    */   
/*  63:    */   public Ciudad(int idCiudad, String codigo, String nombre)
/*  64:    */   {
/*  65: 97 */     this.idCiudad = idCiudad;
/*  66: 98 */     this.codigo = codigo;
/*  67: 99 */     this.nombre = nombre;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getId()
/*  71:    */   {
/*  72:109 */     return this.idCiudad;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdCiudad()
/*  76:    */   {
/*  77:118 */     return this.idCiudad;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdCiudad(int idCiudad)
/*  81:    */   {
/*  82:128 */     this.idCiudad = idCiudad;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdOrganizacion()
/*  86:    */   {
/*  87:137 */     return this.idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdOrganizacion(int idOrganizacion)
/*  91:    */   {
/*  92:147 */     this.idOrganizacion = idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getIdSucursal()
/*  96:    */   {
/*  97:156 */     return this.idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdSucursal(int idSucursal)
/* 101:    */   {
/* 102:166 */     this.idSucursal = idSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Provincia getProvincia()
/* 106:    */   {
/* 107:175 */     if (this.provincia == null) {
/* 108:176 */       this.provincia = new Provincia();
/* 109:    */     }
/* 110:178 */     return this.provincia;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setProvincia(Provincia provincia)
/* 114:    */   {
/* 115:188 */     this.provincia = provincia;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getCodigo()
/* 119:    */   {
/* 120:197 */     return this.codigo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setCodigo(String codigo)
/* 124:    */   {
/* 125:207 */     this.codigo = codigo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getNombre()
/* 129:    */   {
/* 130:216 */     return this.nombre;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setNombre(String nombre)
/* 134:    */   {
/* 135:226 */     this.nombre = nombre;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getCodigoPostal()
/* 139:    */   {
/* 140:235 */     return this.codigoPostal;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setCodigoPostal(String codigoPostal)
/* 144:    */   {
/* 145:245 */     this.codigoPostal = codigoPostal;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getDescripcion()
/* 149:    */   {
/* 150:254 */     return this.descripcion;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setDescripcion(String descripcion)
/* 154:    */   {
/* 155:264 */     this.descripcion = descripcion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean isActivo()
/* 159:    */   {
/* 160:273 */     return this.activo;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setActivo(boolean activo)
/* 164:    */   {
/* 165:283 */     this.activo = activo;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public List<String> getCamposAuditables()
/* 169:    */   {
/* 170:287 */     List<String> lista = new ArrayList();
/* 171:288 */     lista.add("codigo");
/* 172:289 */     lista.add("nombre");
/* 173:290 */     lista.add("codigo_postal");
/* 174:291 */     lista.add("descripcion");
/* 175:292 */     lista.add("activo");
/* 176:293 */     return lista;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String toString()
/* 180:    */   {
/* 181:298 */     return this.nombre;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean isPredeterminado()
/* 185:    */   {
/* 186:305 */     return this.predeterminado;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setPredeterminado(boolean predeterminado)
/* 190:    */   {
/* 191:312 */     this.predeterminado = predeterminado;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<Parroquia> getListaParroquia()
/* 195:    */   {
/* 196:316 */     return this.listaParroquia;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setListaParroquia(List<Parroquia> listaParroquia)
/* 200:    */   {
/* 201:320 */     this.listaParroquia = listaParroquia;
/* 202:    */   }
/* 203:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Ciudad
 * JD-Core Version:    0.7.0.1
 */