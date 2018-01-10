/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinTable;
/*  12:    */ import javax.persistence.ManyToMany;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="conjunto_atributo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  20:    */ public class ConjuntoAtributo
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="conjunto_atributo", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="conjunto_atributo")
/*  27:    */   @Column(name="id_conjunto_atributo", unique=true, nullable=false)
/*  28:    */   private int idConjuntoAtributo;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="codigo", nullable=false, length=10)
/*  34:    */   @NotNull
/*  35:    */   @Size(max=10)
/*  36:    */   private String codigo;
/*  37:    */   @Column(name="nombre", nullable=false, length=50)
/*  38:    */   @NotNull
/*  39:    */   @Size(max=50)
/*  40:    */   private String nombre;
/*  41:    */   @Column(name="descripcion", length=200)
/*  42:    */   @Size(max=200)
/*  43:    */   private String descripcion;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45:    */   private boolean activo;
/*  46:    */   @Column(name="predeterminado", nullable=false)
/*  47:    */   private boolean predeterminado;
/*  48:    */   @Column(name="indicador_producto")
/*  49:    */   private boolean indicadorProducto;
/*  50:    */   @Column(name="indicador_cliente")
/*  51:    */   private boolean indicadorCliente;
/*  52:    */   @Column(name="indicador_proveedor")
/*  53:    */   private boolean indicadorProveedor;
/*  54:    */   @ManyToMany(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE})
/*  55:    */   @JoinTable(name="conjunto_atributo_atributo", joinColumns={@javax.persistence.JoinColumn(name="id_conjunto_atributo")}, inverseJoinColumns={@javax.persistence.JoinColumn(name="id_atributo")})
/*  56: 84 */   private List<Atributo> listaAtributo = new ArrayList();
/*  57:    */   
/*  58:    */   public int getId()
/*  59:    */   {
/*  60: 93 */     return this.idConjuntoAtributo;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdConjuntoAtributo()
/*  64:    */   {
/*  65: 97 */     return this.idConjuntoAtributo;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdConjuntoAtributo(int idConjuntoAtributo)
/*  69:    */   {
/*  70:101 */     this.idConjuntoAtributo = idConjuntoAtributo;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdOrganizacion()
/*  74:    */   {
/*  75:105 */     return this.idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdOrganizacion(int idOrganizacion)
/*  79:    */   {
/*  80:109 */     this.idOrganizacion = idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Integer getIdSucursal()
/*  84:    */   {
/*  85:113 */     return Integer.valueOf(this.idSucursal);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdSucursal(Integer idSucursal)
/*  89:    */   {
/*  90:117 */     this.idSucursal = idSucursal.intValue();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getCodigo()
/*  94:    */   {
/*  95:121 */     return this.codigo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setCodigo(String codigo)
/*  99:    */   {
/* 100:125 */     this.codigo = codigo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getNombre()
/* 104:    */   {
/* 105:129 */     return this.nombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setNombre(String nombre)
/* 109:    */   {
/* 110:133 */     this.nombre = nombre;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getDescripcion()
/* 114:    */   {
/* 115:137 */     return this.descripcion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setDescripcion(String descripcion)
/* 119:    */   {
/* 120:141 */     this.descripcion = descripcion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean getActivo()
/* 124:    */   {
/* 125:145 */     return this.activo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setActivo(boolean activo)
/* 129:    */   {
/* 130:149 */     this.activo = activo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean getPredeterminado()
/* 134:    */   {
/* 135:153 */     return this.predeterminado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setPredeterminado(boolean predeterminado)
/* 139:    */   {
/* 140:157 */     this.predeterminado = predeterminado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<Atributo> getListaAtributo()
/* 144:    */   {
/* 145:166 */     return this.listaAtributo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setListaAtributo(List<Atributo> listaAtributo)
/* 149:    */   {
/* 150:176 */     this.listaAtributo = listaAtributo;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String toString()
/* 154:    */   {
/* 155:181 */     return this.nombre;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean isIndicadorProducto()
/* 159:    */   {
/* 160:188 */     return this.indicadorProducto;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setIndicadorProducto(boolean indicadorProducto)
/* 164:    */   {
/* 165:196 */     this.indicadorProducto = indicadorProducto;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean isIndicadorCliente()
/* 169:    */   {
/* 170:203 */     return this.indicadorCliente;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setIndicadorCliente(boolean indicadorCliente)
/* 174:    */   {
/* 175:211 */     this.indicadorCliente = indicadorCliente;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isIndicadorProveedor()
/* 179:    */   {
/* 180:218 */     return this.indicadorProveedor;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setIndicadorProveedor(boolean indicadorProveedor)
/* 184:    */   {
/* 185:226 */     this.indicadorProveedor = indicadorProveedor;
/* 186:    */   }
/* 187:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ConjuntoAtributo
 * JD-Core Version:    0.7.0.1
 */