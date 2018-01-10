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
/*  11:    */ import javax.persistence.OneToMany;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="partida_arancelaria", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  18:    */ public class PartidaArancelaria
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = -1938831444100606045L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="partida_arancelaria", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="partida_arancelaria")
/*  25:    */   @Column(name="id_partida_arancelaria")
/*  26:    */   private int idPartidaArancelaria;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", nullable=false, length=10)
/*  32:    */   @Size(min=1, max=10)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false, length=50)
/*  35:    */   @Size(min=3, max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="descripcion", length=200, nullable=true)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="partidaArancelaria")
/*  45: 77 */   private List<DetallePartidaArancelaria> listaDetallePartidaArancelaria = new ArrayList();
/*  46:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="partidaArancelaria")
/*  47: 80 */   private List<Producto> listaProducto = new ArrayList();
/*  48:    */   
/*  49:    */   public PartidaArancelaria() {}
/*  50:    */   
/*  51:    */   public PartidaArancelaria(int idPartidaArancelaria, String codigo, String nombre)
/*  52:    */   {
/*  53:110 */     this.idPartidaArancelaria = idPartidaArancelaria;
/*  54:111 */     this.codigo = codigo;
/*  55:112 */     this.nombre = nombre;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getId()
/*  59:    */   {
/*  60:122 */     return getIdPartidaArancelaria();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdPartidaArancelaria()
/*  64:    */   {
/*  65:135 */     return this.idPartidaArancelaria;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdPartidaArancelaria(int idPartidaArancelaria)
/*  69:    */   {
/*  70:145 */     this.idPartidaArancelaria = idPartidaArancelaria;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdOrganizacion()
/*  74:    */   {
/*  75:154 */     return this.idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdOrganizacion(int idOrganizacion)
/*  79:    */   {
/*  80:164 */     this.idOrganizacion = idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdSucursal()
/*  84:    */   {
/*  85:173 */     return this.idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdSucursal(int idSucursal)
/*  89:    */   {
/*  90:183 */     this.idSucursal = idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getCodigo()
/*  94:    */   {
/*  95:192 */     return this.codigo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setCodigo(String codigo)
/*  99:    */   {
/* 100:202 */     this.codigo = codigo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getNombre()
/* 104:    */   {
/* 105:211 */     return this.nombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setNombre(String nombre)
/* 109:    */   {
/* 110:221 */     this.nombre = nombre;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getDescripcion()
/* 114:    */   {
/* 115:230 */     return this.descripcion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setDescripcion(String descripcion)
/* 119:    */   {
/* 120:240 */     this.descripcion = descripcion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isActivo()
/* 124:    */   {
/* 125:249 */     return this.activo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setActivo(boolean activo)
/* 129:    */   {
/* 130:259 */     this.activo = activo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean isPredeterminado()
/* 134:    */   {
/* 135:268 */     return this.predeterminado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setPredeterminado(boolean predeterminado)
/* 139:    */   {
/* 140:278 */     this.predeterminado = predeterminado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<DetallePartidaArancelaria> getListaDetallePartidaArancelaria()
/* 144:    */   {
/* 145:287 */     return this.listaDetallePartidaArancelaria;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setListaDetallePartidaArancelaria(List<DetallePartidaArancelaria> listaDetallePartidaArancelaria)
/* 149:    */   {
/* 150:297 */     this.listaDetallePartidaArancelaria = listaDetallePartidaArancelaria;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<Producto> getListaProducto()
/* 154:    */   {
/* 155:306 */     return this.listaProducto;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setListaProducto(List<Producto> listaProducto)
/* 159:    */   {
/* 160:316 */     this.listaProducto = listaProducto;
/* 161:    */   }
/* 162:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PartidaArancelaria
 * JD-Core Version:    0.7.0.1
 */