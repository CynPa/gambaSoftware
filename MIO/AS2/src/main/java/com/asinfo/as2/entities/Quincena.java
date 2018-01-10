/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.DiaMes;
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
/*  17:    */ import javax.validation.constraints.Min;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="quincena", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"}), @javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"})})
/*  23:    */ public class Quincena
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="quincena", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="quincena")
/*  30:    */   @Column(name="id_quincena")
/*  31:    */   private int idQuincena;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="codigo", length=10, nullable=false)
/*  37:    */   @Size(min=2, max=10)
/*  38:    */   @NotNull
/*  39:    */   private String codigo;
/*  40:    */   @Column(name="nombre", length=50)
/*  41:    */   @Size(min=2, max=50)
/*  42:    */   private String nombre;
/*  43:    */   @Column(name="dia_pago", nullable=false)
/*  44:    */   @Enumerated(EnumType.ORDINAL)
/*  45:    */   private DiaMes diaPago;
/*  46:    */   @Column(name="mes_pago", nullable=false)
/*  47:    */   @Min(0L)
/*  48:    */   private int mesPago;
/*  49:    */   @Column(name="descripcion", length=200)
/*  50:    */   @Size(max=200)
/*  51:    */   private String descripcion;
/*  52:    */   @Column(name="activo", nullable=false)
/*  53:    */   private boolean activo;
/*  54:    */   @Column(name="predeterminado", nullable=false)
/*  55:    */   private boolean predeterminado;
/*  56:    */   @Column(name="indicador_finiquito", nullable=false)
/*  57:    */   private boolean indicadorFiniquito;
/*  58:    */   @Column(name="indicador_anticipo", nullable=false)
/*  59:    */   private boolean indicadorAnticipo;
/*  60:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="quincena")
/*  61:103 */   private List<Rubro> listaRubro = new ArrayList();
/*  62:    */   
/*  63:    */   public Quincena() {}
/*  64:    */   
/*  65:    */   public Quincena(int idQuincena, String codigo, String nombre)
/*  66:    */   {
/*  67:122 */     this.idQuincena = idQuincena;
/*  68:123 */     this.codigo = codigo;
/*  69:124 */     this.nombre = nombre;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdQuincena()
/*  73:    */   {
/*  74:137 */     return this.idQuincena;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdQuincena(int idQuincena)
/*  78:    */   {
/*  79:147 */     this.idQuincena = idQuincena;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdOrganizacion()
/*  83:    */   {
/*  84:156 */     return this.idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdOrganizacion(int idOrganizacion)
/*  88:    */   {
/*  89:166 */     this.idOrganizacion = idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdSucursal()
/*  93:    */   {
/*  94:175 */     return this.idSucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdSucursal(int idSucursal)
/*  98:    */   {
/*  99:185 */     this.idSucursal = idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getCodigo()
/* 103:    */   {
/* 104:194 */     return this.codigo;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setCodigo(String codigo)
/* 108:    */   {
/* 109:204 */     this.codigo = codigo;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getNombre()
/* 113:    */   {
/* 114:213 */     return this.nombre;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setNombre(String nombre)
/* 118:    */   {
/* 119:223 */     this.nombre = nombre;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getMesPago()
/* 123:    */   {
/* 124:232 */     return this.mesPago;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setMesPago(int mesPago)
/* 128:    */   {
/* 129:242 */     this.mesPago = mesPago;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getDescripcion()
/* 133:    */   {
/* 134:251 */     return this.descripcion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setDescripcion(String descripcion)
/* 138:    */   {
/* 139:261 */     this.descripcion = descripcion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean isActivo()
/* 143:    */   {
/* 144:270 */     return this.activo;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setActivo(boolean activo)
/* 148:    */   {
/* 149:280 */     this.activo = activo;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean isPredeterminado()
/* 153:    */   {
/* 154:289 */     return this.predeterminado;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setPredeterminado(boolean predeterminado)
/* 158:    */   {
/* 159:299 */     this.predeterminado = predeterminado;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public List<Rubro> getListaRubro()
/* 163:    */   {
/* 164:308 */     return this.listaRubro;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setListaRubro(List<Rubro> listaRubro)
/* 168:    */   {
/* 169:318 */     this.listaRubro = listaRubro;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int getId()
/* 173:    */   {
/* 174:328 */     return this.idQuincena;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public DiaMes getDiaPago()
/* 178:    */   {
/* 179:337 */     return this.diaPago;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setDiaPago(DiaMes diaPago)
/* 183:    */   {
/* 184:347 */     this.diaPago = diaPago;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean isIndicadorFiniquito()
/* 188:    */   {
/* 189:356 */     return this.indicadorFiniquito;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setIndicadorFiniquito(boolean indicadorFiniquito)
/* 193:    */   {
/* 194:366 */     this.indicadorFiniquito = indicadorFiniquito;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public boolean isIndicadorAnticipo()
/* 198:    */   {
/* 199:375 */     return this.indicadorAnticipo;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setIndicadorAnticipo(boolean indicadorAnticipo)
/* 203:    */   {
/* 204:385 */     this.indicadorAnticipo = indicadorAnticipo;
/* 205:    */   }
/* 206:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Quincena
 * JD-Core Version:    0.7.0.1
 */