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
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ import org.hibernate.annotations.ColumnDefault;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="lista_descuentos", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"codigo", "id_organizacion"})})
/*  20:    */ public class ListaDescuentos
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="lista_descuentos", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="lista_descuentos")
/*  27:    */   @Column(name="id_lista_descuentos")
/*  28:    */   private int idListaDescuentos;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="codigo", length=10, nullable=false)
/*  34:    */   @NotNull
/*  35:    */   @Size(max=10)
/*  36:    */   private String codigo;
/*  37:    */   @Column(name="nombre", length=50, nullable=false)
/*  38:    */   @NotNull
/*  39:    */   @Size(max=50)
/*  40:    */   private String nombre;
/*  41:    */   @Column(name="descripcion", length=200)
/*  42:    */   @Size(max=200)
/*  43:    */   private String descripcion;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private boolean activo;
/*  47:    */   @Column(name="predeterminado", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private boolean predeterminado;
/*  50:    */   @Column(name="indicador_descuento_por_producto", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private boolean indicadorDescuentoPorProducto;
/*  53:    */   @Column(name="indicador_carga_automatica", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   @ColumnDefault("'0'")
/*  56:    */   private boolean indicadorCargaAutomatica;
/*  57:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="listaDescuentos")
/*  58: 85 */   private List<VersionListaDescuentos> listaVersionesListaDescuentos = new ArrayList();
/*  59:    */   
/*  60:    */   public int getId()
/*  61:    */   {
/*  62: 93 */     return this.idListaDescuentos;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdListaDescuentos()
/*  66:    */   {
/*  67: 97 */     return this.idListaDescuentos;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdListaDescuentos(int idListaDescuentos)
/*  71:    */   {
/*  72:101 */     this.idListaDescuentos = idListaDescuentos;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdOrganizacion()
/*  76:    */   {
/*  77:105 */     return this.idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdOrganizacion(int idOrganizacion)
/*  81:    */   {
/*  82:109 */     this.idOrganizacion = idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdSucursal()
/*  86:    */   {
/*  87:113 */     return this.idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdSucursal(int idSucursal)
/*  91:    */   {
/*  92:117 */     this.idSucursal = idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getCodigo()
/*  96:    */   {
/*  97:121 */     return this.codigo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setCodigo(String codigo)
/* 101:    */   {
/* 102:125 */     this.codigo = codigo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getNombre()
/* 106:    */   {
/* 107:129 */     return this.nombre;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setNombre(String nombre)
/* 111:    */   {
/* 112:133 */     this.nombre = nombre;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getDescripcion()
/* 116:    */   {
/* 117:137 */     return this.descripcion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setDescripcion(String descripcion)
/* 121:    */   {
/* 122:141 */     this.descripcion = descripcion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isActivo()
/* 126:    */   {
/* 127:145 */     return this.activo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setActivo(boolean activo)
/* 131:    */   {
/* 132:149 */     this.activo = activo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean isPredeterminado()
/* 136:    */   {
/* 137:153 */     return this.predeterminado;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setPredeterminado(boolean predeterminado)
/* 141:    */   {
/* 142:157 */     this.predeterminado = predeterminado;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean isIndicadorDescuentoPorProducto()
/* 146:    */   {
/* 147:161 */     return this.indicadorDescuentoPorProducto;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setIndicadorDescuentoPorProducto(boolean indicadorDescuentoPorProducto)
/* 151:    */   {
/* 152:165 */     this.indicadorDescuentoPorProducto = indicadorDescuentoPorProducto;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean isIndicadorCargaAutomatica()
/* 156:    */   {
/* 157:169 */     return this.indicadorCargaAutomatica;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setIndicadorCargaAutomatica(boolean indicadorCargaAutomatica)
/* 161:    */   {
/* 162:173 */     this.indicadorCargaAutomatica = indicadorCargaAutomatica;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<VersionListaDescuentos> getListaVersionesListaDescuentos()
/* 166:    */   {
/* 167:177 */     return this.listaVersionesListaDescuentos;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setListaVersionesListaDescuentos(List<VersionListaDescuentos> listaVersionesListaDescuentos)
/* 171:    */   {
/* 172:181 */     this.listaVersionesListaDescuentos = listaVersionesListaDescuentos;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<VersionListaDescuentos> getListaVersionListaDescuentosView()
/* 176:    */   {
/* 177:185 */     List<VersionListaDescuentos> lista = new ArrayList();
/* 178:186 */     for (VersionListaDescuentos versionListaDescuentos : this.listaVersionesListaDescuentos) {
/* 179:187 */       if (!versionListaDescuentos.isEliminado()) {
/* 180:188 */         lista.add(versionListaDescuentos);
/* 181:    */       }
/* 182:    */     }
/* 183:191 */     return lista;
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ListaDescuentos
 * JD-Core Version:    0.7.0.1
 */