/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="marca_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  18:    */ public class MarcaProducto
/*  19:    */   extends EntidadBase
/*  20:    */   implements Serializable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="marca_producto", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="marca_producto")
/*  26:    */   @Column(name="id_marca_producto")
/*  27:    */   private int idMarcaProducto;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal")
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="codigo", length=10, nullable=false)
/*  33:    */   @NotNull
/*  34:    */   @Size(min=2, max=10)
/*  35:    */   private String codigo;
/*  36:    */   @Column(name="nombre", length=50, nullable=false)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=50)
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="descripcion", length=200)
/*  41:    */   @Size(max=200)
/*  42:    */   private String descripcion;
/*  43:    */   @Column(name="activo", nullable=false)
/*  44:    */   private boolean activo;
/*  45:    */   @Column(name="predeterminado", nullable=false)
/*  46:    */   private boolean predeterminado;
/*  47:    */   
/*  48:    */   public int getId()
/*  49:    */   {
/*  50: 83 */     return this.idMarcaProducto;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public MarcaProducto() {}
/*  54:    */   
/*  55:    */   public MarcaProducto(int idMarcaProducto)
/*  56:    */   {
/*  57: 94 */     this.idMarcaProducto = idMarcaProducto;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public MarcaProducto(int idMarcaProducto, String nombre)
/*  61:    */   {
/*  62:106 */     this.idMarcaProducto = idMarcaProducto;
/*  63:107 */     this.nombre = nombre;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdMarcaProducto()
/*  67:    */   {
/*  68:116 */     return this.idMarcaProducto;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdMarcaProducto(int idMarcaProducto)
/*  72:    */   {
/*  73:126 */     this.idMarcaProducto = idMarcaProducto;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getCodigo()
/*  77:    */   {
/*  78:130 */     return this.codigo;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setCodigo(String codigo)
/*  82:    */   {
/*  83:134 */     this.codigo = codigo;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getNombre()
/*  87:    */   {
/*  88:143 */     return this.nombre;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setNombre(String nombre)
/*  92:    */   {
/*  93:153 */     this.nombre = nombre;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getDescripcion()
/*  97:    */   {
/*  98:162 */     return this.descripcion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setDescripcion(String descripcion)
/* 102:    */   {
/* 103:172 */     this.descripcion = descripcion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isActivo()
/* 107:    */   {
/* 108:181 */     return this.activo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setActivo(boolean activo)
/* 112:    */   {
/* 113:191 */     this.activo = activo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isPredeterminado()
/* 117:    */   {
/* 118:200 */     return this.predeterminado;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setPredeterminado(boolean predeterminado)
/* 122:    */   {
/* 123:210 */     this.predeterminado = predeterminado;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getIdOrganizacion()
/* 127:    */   {
/* 128:219 */     return this.idOrganizacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setIdOrganizacion(int idOrganizacion)
/* 132:    */   {
/* 133:229 */     this.idOrganizacion = idOrganizacion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getIdSucursal()
/* 137:    */   {
/* 138:233 */     return this.idSucursal;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setIdSucursal(int idSucursal)
/* 142:    */   {
/* 143:237 */     this.idSucursal = idSucursal;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String toString()
/* 147:    */   {
/* 148:247 */     return this.nombre;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public List<String> getCamposAuditables()
/* 152:    */   {
/* 153:251 */     ArrayList<String> lista = new ArrayList();
/* 154:252 */     lista.add("codigo");
/* 155:253 */     lista.add("nombre");
/* 156:254 */     lista.add("descripcion");
/* 157:255 */     lista.add("activo");
/* 158:    */     
/* 159:257 */     return lista;
/* 160:    */   }
/* 161:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MarcaProducto
 * JD-Core Version:    0.7.0.1
 */