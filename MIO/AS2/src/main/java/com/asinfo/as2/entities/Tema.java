/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ 
/*  11:    */ @Table(name="tema", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"codigo"})})
/*  12:    */ @Entity
/*  13:    */ public class Tema
/*  14:    */   extends EntidadBase
/*  15:    */ {
/*  16:    */   private static final long serialVersionUID = 1L;
/*  17:    */   @Id
/*  18:    */   @TableGenerator(name="tema", initialValue=0, allocationSize=50)
/*  19:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tema")
/*  20:    */   @Column(name="id_tema")
/*  21:    */   private int idTema;
/*  22:    */   @Column(name="codigo", length=20, nullable=false)
/*  23:    */   private String codigo;
/*  24:    */   @Column(name="nombre", length=50, nullable=false)
/*  25:    */   private String nombre;
/*  26:    */   @Column(name="imagen", length=50, nullable=false)
/*  27:    */   private String imagen;
/*  28:    */   
/*  29:    */   public int getId()
/*  30:    */   {
/*  31: 71 */     return this.idTema;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toString()
/*  35:    */   {
/*  36: 76 */     return this.codigo;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getIdTema()
/*  40:    */   {
/*  41: 84 */     return this.idTema;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setIdTema(int idTema)
/*  45:    */   {
/*  46: 92 */     this.idTema = idTema;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getNombre()
/*  50:    */   {
/*  51:100 */     return this.nombre;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setNombre(String nombre)
/*  55:    */   {
/*  56:108 */     this.nombre = nombre;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getImagen()
/*  60:    */   {
/*  61:116 */     return this.imagen;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setImagen(String imagen)
/*  65:    */   {
/*  66:124 */     this.imagen = imagen;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getCodigo()
/*  70:    */   {
/*  71:132 */     return this.codigo;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setCodigo(String codigo)
/*  75:    */   {
/*  76:140 */     this.codigo = codigo;
/*  77:    */   }
/*  78:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Tema
 * JD-Core Version:    0.7.0.1
 */